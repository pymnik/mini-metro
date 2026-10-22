# Mini-Metro
## By Lasse Zegers and Nikita Kušiks
Mini-Metro is a simplified version of the game Mini Metro. The player constructs 
metro-lines between stations to get passengers to their destination, represented 
be their respective shape (A circle passenger must be delivered to a circle station). 
Once stations overflow, the game ends. As a player you try to construct such a 
network to last as long as possible without a metro-station overflowing. 
Each metro-line has one train with a capacity of 4 passengers and over time more 
metro-stations will appear to increase difficulty.

#### Requirements:
- Java Development Kit version 26 or newer
- Build tool: Maven 3.x, installed and available on the PATH (check with 
`mvn -version`) Download it from https://maven.apache.org/download.cgi
- Access to internet on the first build

#### Build and run:
1. Unzip the submitted archive (or clone the repository) and open your terminal
in the main project folder (containing `pom.xml`)
2. The following command runs and builds the game:
```mvn compile exec:java```
3. The game window opens. The first run takes longer due to the fact that Maven
downloads the dependencies.

**Alternative (NetBeans):** open the project with *File -> Open Project*, then run
the class `MiniMetro`

##### Note:
Warnings about native access from FlatLaf in the console are harmless.

#### How to play:
##### General:
Once the game opens passengers, represented by shapes, start spawning at the four
initialized stations. Using the buttons on the right you can: pause the game, 
restart the game and/or update the game-speed. 

##### Draw/remove metro-lines:
On the right you can select which of the three line you would like to build.
Then you click the metro-stations you would like to connect, in the order you 
want to connect them. Once a line is created a train spawns and starts running
back and forth between the metro-stations (unless the game is paused).
To remove a metro-line you select the line you want to remove, and press "Clear line".
Note: the entire line and its train will be removed. If there were passengers in
a train when the line was removed, these will be redistributed.

##### Statistics:
The statistics display:
- Time: how long you have lasted
- Delivered: how many passengers have been delivered to a station with their shape
- Waiting: the amount of passengers waiting at a metro-station
- On trains: how many passengers currently are in a metro-train.
- Most Crowded: the metro-station with the most passengers waiting.

#### Project structure:
The code is divided into three parts.
##### Logic: 
- WorldState: holds the state of the game: stations, line, trains etc.
- Statistics: counter shown in the sidebar
- The objects of the game: Station, MetroLine, Train, Passenger
- StationShape
##### Rendering and GUI:
- Renderer: does all the drawing 
- MiniMetroGUI: the window built with the GUI builder of NetBeans
##### Mechanics:
- The rules of the game: spawning passengers, moving trains, pause and speed etc.
##### How is it connected?
Every tick mechanics is updated, which changes the data in WorldState and then
the renderer repaints.