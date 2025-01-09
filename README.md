# Breakout - An Android Project
Created by: Ricardo Lopes



## Estrutura do projecto
O projeto segue a arquitetura MVVM, onde source files com o nome Screen são responsáveis pelo UI, View são responsáveis pela lógica do UI. Além disto estão presente também class files com nomes únicos tais como "Ball", "Brick", "Paddle", "Star"; estes são objetos usados para o desenvolvimento do jogo. Têm-se o model Highscore que serve para representar os dados de Highscore da base de dados e finalmente o HighscoreRepo que contém as funções responsáveis por obter a Highscore da base de dados e atualizar a mesma.

```
com.example.game
.
.....models
..............Highscore
	
.....repo
..............HighscoreRepo
.
.
....Ball
....Brick
....Paddle
....Star
.
.
....GameHome
....GameOverScreen
....GameScreen
....HighscoreScreen
....LoginScreen
.
.
....GameView
....LoginView
....HighscoreView
....MainActivity
	
```

```kotlin
class Highscore (
    var userId : String?,
    var name : String?,
    var score : Int?,
    var error: String?) {

    constructor() : this(null,null,0, null)
}

```

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            GameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login"){
                            LoginView(onLogin = {
                                navController.navigate("game_start")
                            })
                        }

                        composable("game_start") {
                            GameHome(onPlayClick = {
                                navController.navigate("game_screen")
                            },
                                onHighscoreClick = {
                                    navController.navigate("highscore")
                                })
                        }

                        composable("game_screen") {
                            GameScreenView(onGameOver = {
                                navController.navigate("gameover_screen")
                            })
                        }

                        composable("gameover_screen") {
                            GameoverScreen(onPlayGame = {
                                navController.navigate("game_screen")
                            },
                                onMainMenu = {
                                    navController.navigate("game_start")
                                })
                        }

                        composable("highscore"){
                            HighscoreScreen(onReturn = {
                                navController.navigate("game_start")
                            })
                        }
                    }
                }
            }
        }
    }
}
```

## Lista de funcionalidade da aplicação
Esta aplicação apresenta as seguintes funcionalidades:

 - Autenticação por email.
 - Alojamento da Highscore do utilizador numa base de dados remota.
 - Um jogo estilo Breakout infinito que só termina quando o utilizador deixa a bola cair para além da "paddle".

## Desenhos, esquemas e protótipos da aplicação
Este projeto não fez uso de desenhos nem esquemas, sendo apenas usado a conversão de um projeto anterior feito em Python para Kotlin. O resto da aplicação foi elaborada a partir de exemplos feitos em aula para adicionar as funcionalidades pretendidas.

```Python
import os, time, pygame, random, copy

# Window Dimensions
SCREEN_WIDTH = 600
SCREEN_HEIGHT = 450

# Colors
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
RED = (255, 0, 0)
GREEN = (0, 255, 0)
BLUE = (0, 0, 255)

class Paddle:
    def __init__(self):
        self.width = 100
        self.height = 10
        self.x = SCREEN_WIDTH / 2 - self.width / 2  # Center Horizontally
        self.y = SCREEN_HEIGHT - 30 # Center Vertically
        self.speed = 2 # Arbitrary Speed
        self.previous_center_x = self.x  # Initialize previous center x position
        self.deadzone = 5 # Minimum ammount of movement required
        self.inverse_deadzone = 75 # Maximum ammount of movement allowed
        self.scale_conversion = 2.0 # 

    def update_position(self, center_x):
        if center_x:
            #current_center_x = center_coordinates[0]
            # Calculate the distance moved since the last frame
            delta_x = (center_x - self.previous_center_x) * self.scale_conversion

            # Check if the movement exceeds the dead zone or goes beyond the inverse deadzone
            if abs(delta_x) > self.deadzone and abs(delta_x) < self.inverse_deadzone:
                self.x += delta_x  # Update paddle position based on the calculated delta

            # Ensure the paddle stays within the screen boundaries
            self.x = max(0, min(self.x, SCREEN_WIDTH - self.width))

            # Update previous center x position
            self.previous_center_x = center_x

    def draw(self, screen):
            pygame.draw.rect(screen, BLUE, (self.x, self.y, self.width, self.height))

    """def move(self, direction, screen):
        if direction == "left" and self.x > 0:
            self.x -= self.speed
            
        if direction == "right" and self.x < SCREEN_WIDTH - self.width:
            self.x += self.speed"""


# Ball class
class Ball:
    def __init__(self):
        self.reset()

    def move(self):
        self.x += self.dx
        self.y += self.dy

        # Bounce off walls
        if self.x - self.radius < 0 or self.x + self.radius > SCREEN_WIDTH:
            self.dx *= -1 #Invert Acceleration
            
        if self.y - self.radius < 0: # Bounce top
            self.dy *= -1 # Invert negative acceleration into positive acceleration

    def draw(self, screen):
        pygame.draw.circle(screen, RED, (int(self.x), int(self.y)), self.radius)

    def reset(self):
        self.x = SCREEN_WIDTH / 2 # Center Coordinate
        self.y = SCREEN_HEIGHT / 2 # Center Coordinate
        self.radius = 10
        self.dx = 4 * random.choice([-1, 1]) # Horizontal acceleration (random choice between left and right for randomness)
        self.dy = -4 # Vertical Acceleration


# Block class
class Block:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.width = 60
        self.height = 20
        self.color = random.choice([GREEN, WHITE, BLUE])
        self.is_visible = True # When hit become invisible

    def draw(self, screen):
        if self.is_visible:
            pygame.draw.rect(screen, self.color, (self.x, self.y, self.width, self.height))


def main():
    pygame.init()
    screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT)) # Create Window
    pygame.display.set_caption("Breakout Game") # Title
    clock = pygame.time.Clock()
    
    paddle = Paddle()
    ball = Ball()
    block_template = Block(0, 0) # Object to get width and height properties from (not in the mood to use global variables)

    # Create blocks
    blocks = []
    for row in range(5):
        for col in range(8):
            block_x = col * (block_template.width + 5) + 40 # Column number * (BlockWidth + spacing) + padding
            block_y = row * (block_template.height + 5) + 30 # Same thing but height
            blocks.append(Block(block_x, block_y)) # Add to object list

    game_started = False # Bruh
    
    while True:
        # Event handling
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                cv_worker_running = False # Terminate cv_worker thread
                cv_worker.join() # Wait until cv_worker thread returns
                cam.release() # Release camera object
                cv2.destroyAllWindows() # Close all opencv windows
                pygame.quit() # Close pygame
                return 0

            if event.type == pygame.MOUSEBUTTONDOWN and not game_started: # Prevent game from auto starting
                game_started = True
    
        if game_started: # Bruh 2
            #center_x = random.randrange(0, SCREEN_WIDTH)
            paddle.update_position(center_x)  # Update paddle position based on detected object

            # Move the ball
            ball.move()

            # Check ball collision with paddle (if ball within paddle dimensions min < ball < max)
            if (paddle.x < ball.x < paddle.x + paddle.width) and (paddle.y < ball.y + ball.radius < paddle.y + paddle.height):
                ball.dy *= -1

                # Calculate where the ball hit the paddle (calculate difference from rectangle origin in relation to ball, divide by paddle.width to normalize on a scale of 0 to 1)
                hit_pos = (ball.x - paddle.x) / paddle.width

                # Adjust the ball's direction based on where it hits the paddle (8 acceleration * position -0.5 to determine if left or right, ball.x < paddle.x + width/ 2 = neg, ball.x > paddle.x + width/2 = pos)
                ball.dx = 8 * (hit_pos - 0.5)
            
            # Check ball collision with blocks
            for block in blocks:
                if block.is_visible and (block.x < ball.x < block.x + block.width) and (block.y < ball.y < block.y + block.height): # Ball within block dimensions
                    ball.dy *= -1 # Invert vertical movement (yes if it hits sideways while doing down it goes up)
                    block.is_visible = False

            # Check if the ball falls below the paddle
            if ball.y > SCREEN_HEIGHT:
                ball.reset() 

            # Draw everything
            screen.fill(BLACK)  # Clear screen before drawing
            paddle.draw(screen)
            ball.draw(screen)

            for block in blocks:
                block.draw(screen)

            # Update the screen
            pygame.display.flip()

        clock.tick(60)
```


## Modelo de dados
Para este projeto, não foi necessário o uso de modelos de dados complexos, sendo apenas utilizado o modelo Highscore para receber os dados necessarios:

```kotlin
class Highscore (
    var userId : String?,
    var name : String?,
    var score : Int?,
    var error: String?) {

    constructor() : this(null,null,0, null)
}
```

## Implementação do projecto
O projeto foi implementado seguindo a arquitetura MVVM e de acordo com os conteúdos lecionados nas aulas. Para tal fez-se uso da documentação Android e dos exercícios de aula: https://github.com/lgleto/EDJD2425


## Tecnologias usadas
As tecnologias utilizadas para este projeto foram:

 - Firebase, para autenticação.
 - Firestore, para alojamento de dados.
 - Jetpack Compose, para a criação do UI
 - Android Canvas, utilizado para desenhar manualmente os elementos do jogo no ecra e posiciona-los de forma livre.

## Dificuldades
Na elaboração deste projeto ouve uma falha no planeamento, o qual levou a uma entrega tardia. De futuro é necessário alocar mais tempo para a elaboração de um projeto desta scope.

### Conclusões
Este projeto serviu como base para aperfeiçoar e por em prática os conhecimentos obtidos durante as aulas.
