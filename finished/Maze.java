public class Maze {

    private static final int[] DIRECTIONS = new int[]{1, 0, -1, 0, 1};

    static String[][] EXAMPLE_MAZE = {
            {
                    "  #  ",
                    "# # #",
                    "     ",
                    "#### ",
                    "     ",
            },{
                    "     ",
                    " ####",
                    "     ",
                    "#### ",
                    "     ",
            },
            {
                    "  #  ",
                    "#   #",
                    "## ##",
                    "#   #",
                    "  #  ",
            },
            {
                    "    #  ## ",
                    " ##   #   ",
                    "   # #  ##",
                    "# #  # ## ",
                    " #  ##    ",
                    "   #  # # ",
                    " #   #  # ",
                    " ## #  #  ",
                    " ##  ##  #",
                    "   #    ##",
            },
            {
                    " # # ",
                    "   # ",
                    "# ## ",
                    "  #  ",
                    "# # #",
                    "  #  ",
                    " #  #",
                    "   # ",
                    " ##  ",
                    "    #",
            },
            {
                    "   #    # ",
                    " #  # # # ",
                    "  #   #   ",
            }
    };

    static int[][][] EXAMPLE_PATH = {
            {{0,0}, {1,0}, {2,0}, {3,0}, {4,0}, {4,1}, {4,2}, {3,2}, {3,3},
                    {3,4}, {4,4}},
            // =============
            {{0,0}, {1,0}, {2,0}, {3,0}, {4,0}, {4,1}, {4,2}, {3,2}, {2,2},
                    {1,2}, {0,2}, {0,3}, {0,4}, {1,4}, {2,4}, {3,4}, {4,4}},
            // =============
            {{0,0}, {1,0}, {1,1}, {2,1}, {2,2}, {2,3}, {3,3}, {3,4}, {4,4}},
            // =============
            {{0,0}, {0,1}, {0,2}, {0,3}, {0,4}, {1,4}, {2,4}, {2,3}, {3,3},
                    {3,2}, {3,1}, {4,1}, {4,0}, {5,0}, {6,0}, {7,0}, {7,1},
                    {8,1}, {8,2}, {9,2}, {9,3}, {9,4}, {9,5}, {8,5}, {7,5},
                    {6,5}, {6,6}, {6,7}, {7,7}, {7,8}, {8,8}, {9,8}, {9,9}},
            // =============
            {{0,0}, {0,1}, {0,2}, {1,2}, {2,2}, {2,3}, {3,3}, {3,4}, {3,5},
                    {3,6}, {4,6}, {4,7}, {4,8}, {4,9}},
            //==============
            {{0,0}, {0,1}, {0,2}, {1,2}, {2,2}, {2,1}, {3,1}, {3,0}, {4,0},
                    {5,0}, {5,1}, {5,2}, {6,2}, {7,2}, {7,1}, {7,0}, {8,0},
                    {9,0}, {9,1}, {9,2}}
    };

    /**
     * Load the example maze with the given index
     * @param index the index of the example maze
     * @return the maze as a boolean array, true if the cell is walkable, false if it is a wall
     */
    public static boolean[][] loadExampleMaze( int index ) {
        if ( index < 0 || index >= EXAMPLE_MAZE.length ) {
            System.out.printf("Index must be between 0 and %d \n", EXAMPLE_MAZE.length - 1 );
            System.exit( 2 );
        }
        return loadMaze( EXAMPLE_MAZE[index] );
    }

    /**
     * Return the char if it is one of the given characters, otherwise exit the program
     * @param args the arguments
     * @param index the index of the argument
     * @param c the characters to check
     * @return the char
     */
    public static char getChar( String[] args, int index, char... c ) {
        String s = args[index];
        if ( s.length() == 1 ) {
            for ( char c1 : c ) {
                if ( s.charAt(0) == c1 ) {
                    return c1;
                }
            }
        }
        String listOfChars = "";
        String sep = " ";
        for ( int i = 0; i < c.length; i++ ) {
            listOfChars += sep + "'" +  c[i] + "'";
            sep = i == c.length - 2 ? " and " : ", ";
        }
        System.out.printf("Only the characters%s are allowed as argument at position %d!", listOfChars, index + 1 );
        System.exit( 1 );
        return 0;
    }

    /**
     * Argument 0: d for demo mode and i for interactive mode, if demo mode, the next argument is the index of the example maze
     * Argument 1: debug, 1 to show coordinates, 0 to hide (useful for large mazes)
     * if not demo mode:
     *       Argument 2: r for random maze, next argument is then the width and thereafter the height,
     *                   e for example maze, where next argument is the index of the example maze,
     *                   l for load maze, where next argument is the maze as a string with each row separated by a comma
     *       Argument 3: strategy, r for recursive, c for recursive cached, i for iterative, a for any
     *       Argument 4: true to allow shortcuts, false to disallow shortcuts (optional)
     * @param args the arguments
     */
    public static void main( String[] args ) {

        int argIndex = 0;
        boolean[][] maze;
        int demoIndex = -1;
        boolean shortcuts = false;

        char modeChar = getChar(args, argIndex++, 'd', 'i' );
        if ( modeChar == 'd' ) {
            demoIndex = Integer.parseInt( args[argIndex++] );
        }

        boolean debug = getChar( args, argIndex++, '1', '0' ) == '1';

        char mazeModeChar = 'e';
        char strategyChar = 'r';
        int width = -1;
        int height = -1;

        String[] mazeArgs = new String[0];

        if( modeChar != 'd' ) {
            mazeModeChar = getChar( args, argIndex++, 'r', 'e', 'l' );
            if ( mazeModeChar == 'r' ) {
                width = Integer.parseInt( args[argIndex++] );
                height = Integer.parseInt( args[argIndex++] );
            } else if ( mazeModeChar == 'e' ) {
                demoIndex = Integer.parseInt( args[argIndex++] );
            } else {
                mazeArgs = args[argIndex++].split(",");
            }
            strategyChar = getChar(args, argIndex++, 'r', 'c', 'i', 'a' );

            if ( args.length == argIndex + 1 ) {
                shortcuts =  getChar(args, argIndex, '1', '0' ) == '1';
            }
        }

        if ( mazeModeChar == 'e' ) {
            maze = loadExampleMaze( demoIndex );
        } else if ( mazeModeChar == 'l' ) {
            maze = loadMaze( mazeArgs );
        } else {
            maze = new boolean[width][height];
            generateRandomMaze( maze, width - 1, height - 1, shortcuts );
        }

        printMazeGUI( maze, debug );

        if ( modeChar == 'i' ) {
            interactiveMode( maze, strategyChar, debug );
        } else {
            demoMode( debug, demoIndex );
        }
    }

    public static void demoMode( boolean debug, int index ) {
        updatePath( new int[0][0], EXAMPLE_PATH[index], debug );
    }

    /**
     * Interactive mode, the user can click on the maze to get the shortest path to the clicked cell
     * @param maze the maze
     * @param strategyChar the strategy to use, r for recursive, c for recursive cached, i for iterative
     * @param debug true to show coordinates, false to hide
     */
    public static void interactiveMode( boolean[][] maze, char strategyChar, boolean debug ) {
        int endX = maze.length - 1;
        int endY = maze[endX].length - 1;
        int lastX = -1;
        int lastY = -1;
        int[][] lastPath = new int[0][0];

        while ( true ) {
            if ( StdDraw.mousePressed() ) {
                int x = (int) (StdDraw.mouseX() - 0.5);
                int y = (int) (StdDraw.mouseY() - 0.5);
                if ( !isWall( maze, x, y ) && (x != lastX || y != lastY) ) {
                    int[][] path = getPath( maze, x, y, endX, endY, strategyChar );
                    if ( isValidPath( maze, path ) ) {
                        updatePath( lastPath, path, debug );
                        lastPath = path;
                        lastX = x;
                        lastY = y;
                    } else {
                        System.out.println("Invalid path");
                    }
                }
            }
            // no need to check permanently, 10 ms are still 100 checks per second
            StdDraw.pause( 10 );
        }
    }

    /**
     * Print the maze to the UI
     * @param maze the maze
     * @param debug true to show coordinates, false to hide
     */
    public static void printMazeGUI( boolean[][] maze, boolean debug ) {
        int width = 400;
        double ratio = (double) maze[0].length / maze.length;
        StdDraw.setCanvasSize(width, (int) (width * ratio));
        StdDraw.setXscale(0, (int)(maze[0].length / ratio) + 1);
        StdDraw.setYscale(0, maze[0].length + 1);
        for ( int x = -1; x < maze.length + 1; x++ ) {
            for ( int y = -1; y < maze[0].length + 1; y++ ) {
                if ( x == maze.length && y == maze[0].length - 1 ) {
                    StdDraw.setPenColor( StdDraw.BLUE );
                    StdDraw.filledSquare(x + 1, y + 1, 0.5);
                } else if ( isWall( maze, x, y ) ) {
                    StdDraw.setPenColor( StdDraw.BLACK );
                    StdDraw.filledSquare(x + 1, y + 1, 0.5);
                    StdDraw.setPenColor( StdDraw.WHITE );
                } else {
                    StdDraw.setPenColor( StdDraw.BLACK );
                }
                if ( debug && x >= 0 && y >= 0 && x < maze.length && y < maze[0].length ) {
                    StdDraw.text( x + 1, y + 1, "(" + x + "," + y + ")");
                }
            }
        }
    }

    /**
     * Returns an array with numbers between 0 and 3 in a random order
     * @return the array with random directions
     */
    private static int[] getRandomDirections() {
        int[] directions = new int[4];
        for ( int i = 0; i < 4; i++ ) {
            directions[i] = i;
        }
        for ( int i = 0; i < 4; i++ ) {
            int randomIndexToSwap = (int) ( Math.random() * 4 );
            int temp = directions[randomIndexToSwap];
            directions[randomIndexToSwap] = directions[i];
            directions[i] = temp;
        }
        return directions;
    }

    /**
     * Build a random maze starting from the given coordinates and with the current maze
     * @param maze the current maze, (x,y) will be set to true if it is walkable
     * @param x the x coordinate to check
     * @param y the y coordinate to check
     * @param allowShortcuts true to allow shortcuts, false to disallow shortcuts
     */
    public static void generateRandomMaze( boolean[][] maze, int x, int y, boolean allowShortcuts ) {

        if ( isOut(maze, x, y) || maze[x][y] ) {
            // out of bounds or already walkable
            return;
        }

        // calculate how many walls are around the current cell
        byte walls = 0;
        for ( int i = 0; i < 4; i++ ) {
            int x1 = x + DIRECTIONS[i];
            int y1 = y + DIRECTIONS[i + 1];
            if ( isWall( maze, x1, y1 ) ) {
                walls++;
            }
        }

        if ( allowShortcuts ) {
            // 2 walls means it creates a path, only do it 10% of the time
            if (walls < 2 || walls == 2 && Math.random() < 0.9 ){
                return;
            }
        } else {
            // We do not allow shortcuts
            if ( walls < 3 ) {
                return;
            }
        }

        // mark the current cell as walkable
        maze[x][y] = true;

        // randomize which direction to go
        final int[] directions = getRandomDirections();

        // recursively build the maze
        for ( int direction : directions ) {
            generateRandomMaze( maze, x + DIRECTIONS[ direction ], y + DIRECTIONS[ direction + 1 ], allowShortcuts );
        }
    }

    /* ================== Do not change the code above (except adding new mazes) ================== */

    /**
     * Load the maze from the given string array
     * @param maze the maze as a string array
     * @return the maze as a boolean array, true if the cell is walkable, false if it is a wall
     */
    public static boolean[][] loadMaze( String[] maze ) {
        boolean[][] out = new boolean[maze[0].length()][maze.length];
        for (int i = 0; i <= maze.length-1; i++) {
            for (int j = 0; j <= maze[i].length()-1; j++) {
                if(maze[i].charAt(j) == ' ') {
                    out[j][maze.length-1-i] = true;
                }
                else if(maze[i].charAt(j) == '#') {
                    out[j][maze.length-1-i] = false;
                }
            }
        }
        return out;
    }

    /**
     * Check if the given coordinates are out of bounds
     * @param maze the maze
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if the coordinates are out of bounds
     */
        public static boolean isOut ( boolean[][] maze, int x, int y){
            if(maze.length == x && maze[0].length - 1 == y) {
                return false;
            }
            return x < 0 || y < 0 || x >= maze.length || y >= maze[0].length;
        }


        /**
         * Check if the given coordinates are a wall, the maze is surrounded by walls
         * @param maze the maze
         * @param x the x coordinate
         * @param y the y coordinate
         * @return true if the coordinates are a wall
         */
    public static boolean isWall( boolean[][] maze, int x, int y ) {
        if(maze.length == x && maze[0].length - 1 == y) {
            return false;
        }
        if (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length) {
            if (!maze[x][y]) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Update the path in the UI
     * @param pathBefore the path before
     * @param pathNew the new path
     * @param debug true to show coordinates, false to hide
     */
    public static void updatePath( int[][] pathBefore, int[][] pathNew, boolean debug ) {
        for (int i = 0; i < pathBefore.length; i++) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(pathBefore[i][0] + 1, pathBefore[i][1] + 1, 0.5);
        }
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledSquare(1, 1, 0.5);
        if(debug) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(1, 1, "(0,0)");
        }
        for (int i = 1; i < pathNew.length; i++) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.filledSquare(pathNew[i][0] + 1, pathNew[i][1] + 1, 0.5);
            if (debug) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(pathNew[i][0] +1 , pathNew[i][1] + 1, "(" + pathNew[i][0] + "," + pathNew[i][1] + ")");
            }
        }
    }
    /**
     * Check if the given path is valid, the path must not contain walls and steps must be to adjacent cells
     * @param maze the maze
     * @param path the path
     * @return true if the path is valid
     */
    public static boolean isValidPath( boolean[][] maze, int[][] path ) {
        // Überprüfe, ob der Pfad leer ist
        if (path.length == 0) {
            return false;  // Ein leerer Pfad ist nicht gültig
        }

        // Überprüfe den ersten Schritt im Labyrinth
        int firstX = path[0][0];
        int firstY = path[0][1];
        if (isOut(maze, firstX, firstY) || isWall(maze, firstX, firstY)) {
            return false;  // Der erste Schritt ist ungültig
        }

        // Überprüfe alle Schritte des Pfades
        for (int i = 0; i < path.length - 1; i++) {  // Schleife bis zum vorletzten Element
            int currentX = path[i][0];
            int currentY = path[i][1];
            int nextX = path[i + 1][0];
            int nextY = path[i + 1][1];

            // Überprüfen, ob der aktuelle Schritt eine Wand ist oder aus dem Labyrinth führt
            if (isOut(maze, currentX, currentY) || isWall(maze, currentX, currentY)) {
                return false;  // Der aktuelle Schritt ist ungültig
            }

            // Überprüfen, ob der nächste Schritt benachbart zum aktuellen Schritt ist
            if ((currentX == nextX && (currentY == nextY - 1 || currentY == nextY + 1)) ||  // Nach oben oder unten
                    (currentY == nextY && (currentX == nextX - 1 || currentX == nextX + 1))) {  // Nach links oder rechts
                // Der nächste Schritt ist benachbart
            } else {
                return false;  // Die Schritte sind nicht benachbart
            }
        }

        // Überprüfe den letzten Schritt im Labyrinth
        int lastX = path[path.length - 1][0];
        int lastY = path[path.length - 1][1];
        if (isOut(maze, lastX, lastY) || isWall(maze, lastX, lastY)) {
            return false;  // Der letzte Schritt ist ungültig
        }

        return true;  // Der Pfad ist gültig
    }

    /**
     * Compare two paths, the paths must have the same length and the same cells
     * This method basically checks if both arrays are exactly the same
     * @param a the first path
     * @param b the second path
     * @return true if the paths are the same
     */
    public static boolean comparePath( int[][] a, int[][] b ) {
        // Wenn die Längen der Pfade unterschiedlich sind, sind sie nicht gleich
        if (a.length != b.length) {
            return false;
        }

        // Überprüfe jede Zelle in beiden Pfaden
        for (int i = 0; i < a.length; i++) { // Bis zum letzten Index
            for (int j = 0; j < a[i].length; j++) { // Bis zum letzten Index der inneren Arrays
                if (a[i][j] != b[i][j]) { // Wenn sich ein Wert unterscheidet
                    return false;
                }
            }
        }
        return true; // Pfade sind gleich
    }

    /**
     * This method calculates the shortest path from the start to the destination in the given maze
     * Use rekursion for this method
     * @param maze the maze
     * @param lastX the x coordinate of the last cell (start with -1)
     * @param lastY the y coordinate of the last cell (start with -1)
     * @param currentX the x coordinate of the current cell
     * @param currentY the y coordinate of the current cell
     * @param destinationX the x coordinate of the destination
     * @param destinationY the y coordinate of the destination
     * @return the shortest path length
     */
    public static int getShortestPathLengthRekursiv( boolean[][] maze, int lastX, int lastY, int currentX, int currentY, int destinationX, int destinationY ) {// Wenn außerhalb des Labyrinths oder eine Wand
        if (isOut(maze, currentX, currentY) || isWall(maze, currentX, currentY)) {
            return Integer.MAX_VALUE;  // Unendliche Länge, da diese Zelle nicht erreichbar ist
        }

        // Ziel erreicht
        if (currentX == destinationX && currentY == destinationY) {
            return 0;  // Keine weiteren Schritte nötig
        }

        // Temporär blockieren, um Schleifen zu vermeiden
        maze[currentX][currentY] = false;

        // Rekursive Suche in alle Richtungen
        int shortestPathLength = Integer.MAX_VALUE;

        // Nach oben (aber nicht zurück zu dem Punkt, von dem wir kamen)
        if (currentX - 1 != lastX) {
            int length = getShortestPathLengthRekursiv(maze, currentX, currentY, currentX - 1, currentY, destinationX, destinationY);
            if (length != Integer.MAX_VALUE) {
                shortestPathLength = Math.min(shortestPathLength, length + 1);
            }
        }

        // Nach unten
        if (currentX + 1 != lastX) {
            int length = getShortestPathLengthRekursiv(maze, currentX, currentY, currentX + 1, currentY, destinationX, destinationY);
            if (length != Integer.MAX_VALUE) {
                shortestPathLength = Math.min(shortestPathLength, length + 1);
            }
        }

        // Nach links
        if (currentY - 1 != lastY) {
            int length = getShortestPathLengthRekursiv(maze, currentX, currentY, currentX, currentY - 1, destinationX, destinationY);
            if (length != Integer.MAX_VALUE) {
                shortestPathLength = Math.min(shortestPathLength, length + 1);
            }
        }

        // Nach rechts
        if (currentY + 1 != lastY) {
            int length = getShortestPathLengthRekursiv(maze, currentX, currentY, currentX, currentY + 1, destinationX, destinationY);
            if (length != Integer.MAX_VALUE) {
                shortestPathLength = Math.min(shortestPathLength, length + 1);
            }
        }

        // Entblockieren
        maze[currentX][currentY] = false;

        return shortestPathLength;
    }

    /**
     * This method calculates the shortest path from the start to the destination in the given maze
     * Use getShortestPathLengthRekursiv to get the path
     * @param maze the maze
     * @param startX the x coordinate of the start
     * @param startY the y coordinate of the start
     * @param destinationX the x coordinate of the destination
     * @param destinationY the y coordinate of the destination
     * @return a two-dimensional array containing the path in the format { {startX, startY}, {x2, y2}, ..., {destinationX, destinationY} }
     * The path contains each cell to walk through in the correct order. The path starts with the start cell and ends with the destination cell.
     */


    public static int[][] getShortestPathRecursive( boolean[][] maze, int startX, int startY, int destinationX, int destinationY ) {int currentX = startX;
        int currentY = startY;

// Initialize the path
        int[][] path = new int[maze.length * maze[0].length][2];
        int pathIndex = 0;

// Initialize visited array to avoid loops
        boolean[][] visited = new boolean[maze.length][maze[0].length];

// Check if the start and destination points are valid
        if (!maze[startX][startY] || !maze[destinationX][destinationY]) {
            System.out.println("Start- oder Zielpunkt ist blockiert.");
            return new int[0][]; // No valid path
        }

        System.out.println("Startpunkt: (" + currentX + ", " + currentY + ")");

// Mark the start point as visited
        visited[currentX][currentY] = true;

// Store the start point in the path
        path[pathIndex++] = new int[]{currentX, currentY};

// Recursive pathfinding method
        boolean pathFound = false;
        while (!pathFound) {
            System.out.println("Aktuelle Position: (" + currentX + ", " + currentY + ")");

            // If the destination is reached, break the loop
            if (currentX == destinationX && currentY == destinationY) {
                System.out.println("ZIEL ERREICHT! (" + currentX + ", " + currentY + ")");
                path[pathIndex++] = new int[]{currentX, currentY};
                pathFound = true;
                break;
            }

            // Check all four directions (up, down, left, right)
            boolean moved = false;
            int nextX = currentX;
            int nextY = currentY;

            System.out.println("Überprüfe mögliche Richtungen...");

            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            String[] directionNames = {"oben", "unten", "links", "rechts"};

            for (int i = 0; i < directions.length; i++) {
                int newX = currentX + directions[i][0];
                int newY = currentY + directions[i][1];

                if (newX >= 0 && newX < maze.length && newY >= 0 && newY < maze[0].length
                        && maze[newX][newY] && !visited[newX][newY]) {
                    System.out.println("Versuche nach " + directionNames[i] + " zu gehen.");
                    int length = getShortestPathLengthRekursiv(maze, currentX, currentY, newX, newY, destinationX, destinationY);
                    if (length < Integer.MAX_VALUE) {
                        nextX = newX;
                        nextY = newY;
                        moved = true;
                        break;
                    }
                }
            }

            // If no valid direction was found, abort
            if (!moved) {
                System.out.println("Keine gültige Richtung gefunden. Kein Pfad gefunden.");
                return new int[0][];  // No valid path found
            }

            // Update the position
            currentX = nextX;
            currentY = nextY;

            // Mark the current position as visited
            visited[currentX][currentY] = true;

            // Store the next step in the path
            path[pathIndex++] = new int[]{currentX, currentY};
            System.out.println("Bewege dich zu: (" + currentX + ", " + currentY + ")");
        }

// Return the path
        System.out.println("Finde den kürzesten Pfad abgeschlossen.");
        return path;
    }

    /**
     * This method calculates the shortest path from the start to the destination in the given maze
     * Use rekursion for this method, use a cache matrix
     * @param maze the maze
     * @param cache the cache matrix, at the beginning all values are -1
     * @param lastX the x coordinate of the last cell (start with -1)
     * @param lastY the y coordinate of the last cell (start with -1)
     * @param currentX the x coordinate of the current cell
     * @param currentY the y coordinate of the current cell
     * @param destinationX the x coordinate of the destination
     * @param destinationY the y coordinate of the destination
     * @return the shortest path length
     */
    public static int getShortestPathLengthRekursivCached( boolean[][] maze, int[][] cache, int lastX, int lastY, int currentX, int currentY, int destinationX, int destinationY ) {
        // TODO implement this method
        return 0;
    }

    /**
     * This method calculates the shortest path from the start to the destination in the given maze
     * Use getShortestPathLengthRekursivCached to get the path
     * @param maze the maze
     * @param startX the x coordinate of the start
     * @param startY the y coordinate of the start
     * @param destinationX the x coordinate of the destination
     * @param destinationY the y coordinate of the destination
     * @return a two-dimensional array containing the path in the format { {startX, startY}, {x2, y2}, ..., {destinationX, destinationY} }
     * The path contains each cell to walk through in the correct order. The path starts with the start cell and ends with the destination cell.
     */
    public static int[][] getShortestPathRecursiveCached( boolean[][] maze, int startX, int startY, int destinationX, int destinationY ) {
        // call getShortestPathLengthRekursivCached and calculate the path
        // TODO implement this method
        return new int[0][];
    }

    /**
     * This method calculates the shortest path from the start to the destination in the given maze
     * Do not use rekursion for this method
     * @param maze the maze
     * @param startX the x coordinate of the start
     * @param startY the y coordinate of the start
     * @param destinationX the x coordinate of the destination
     * @param destinationY the y coordinate of the destination
     * @return the shortest path length
     */
    public static int getShortestPathLengthIterative( boolean[][] maze, int startX, int startY, int destinationX, int destinationY ) {
        // TODO implement this method
        return 0;
    }

    /**
     * This method calculates the shortest path from the start to the destination in the given maze
     * Use getShortestPathIterative to get the path
     * @param maze the maze
     * @param startX the x coordinate of the start
     * @param startY the y coordinate of the start
     * @param destinationX the x coordinate of the destination
     * @param destinationY the y coordinate of the destination
     * @return a two-dimensional array containing the path in the format { {startX, startY}, {x2, y2}, ..., {destinationX, destinationY} }
     * The path contains each cell to walk through in the correct order. The path starts with the start cell and ends with the destination cell.
     */
    public static int[][] getShortestPathIterative( boolean[][] maze, int startX, int startY, int destinationX, int destinationY ) {
        // call getShortestPathLengthIterative and calculate the path
        // TODO implement this method
        return new int[0][];
    }

    /**
     * This method calculates the shortest path from the start to the destination in the given maze
     * Use any strategy for this method
     * @param maze the maze
     * @param currentX the x coordinate of the current cell
     * @param currentY the y coordinate of the current cell
     * @param destinationX the x coordinate of the destination
     * @param destinationY the y coordinate of the destination
     * @return the shortest path length
     */
    public static int getShortestPathLengthAny( boolean[][] maze, int currentX, int currentY, int destinationX, int destinationY ) {
        return getShortestPathLengthRekursiv( maze, -1, -1, currentX, currentY, destinationX, destinationY );
    }

    /**
     * This method calculates the shortest path from the start to the destination in the given maze
     * @param maze the maze
     * @param startX the x coordinate of the start
     * @param startY the y coordinate of the start
     * @param destinationX the x coordinate of the destination
     * @param destinationY the y coordinate of the destination
     * @return a two-dimensional array containing the path in the format { {startX, startY}, {x2, y2}, ..., {destinationX, destinationY} }
     * The path contains each cell to walk through in the correct order. The path starts with the start cell and ends with the destination cell.
     */
    public static int[][] getShortestPathAny( boolean[][] maze, int startX, int startY, int destinationX, int destinationY ) {
        return getShortestPathRecursive( maze, startX, startY, destinationX, destinationY );
    }

    /**
     * Get the shortest path from the start to the destination in the given maze for the given strategy
     * @param maze the maze
     * @param startX the x coordinate of the start
     * @param startY the y coordinate of the start
     * @param endX the x coordinate of the destination
     * @param endY the y coordinate of the destination
     * @param strategy the strategy to use, r for recursive, c for recursive cached, i for iterative
     * @return the shortest path for the given strategy
     */
    public static int[][] getPath( boolean[][] maze, int startX, int startY, int endX, int endY, char strategy ) {
        System.out.println("Start: Recursive");
        int[][] pathRecursive = getShortestPathRecursive( maze, startX, startY, endX, endY );
        System.out.printf("Found path with length: %d\n", pathRecursive.length);
        System.out.println("Start: Recursive Cached");
        int[][] pathRecursiveCached = getShortestPathRecursiveCached( maze, startX, startY, endX, endY );
        System.out.printf("Found path with length: %d\n", pathRecursiveCached.length);
        System.out.println("Start: Iterative");
        int[][] pathIterative = getShortestPathIterative( maze, startX, startY, endX, endY );
        System.out.printf("Found path with length: %d\n", pathIterative.length);
        System.out.println("Start: Any");
        int[][] pathAny = getShortestPathAny( maze, startX, startY, endX, endY );
        System.out.printf("Found path with length: %d\n", pathIterative.length);

        // check all paths are the same
        if ( !comparePath( pathRecursive, pathRecursiveCached )
                || !comparePath( pathRecursive, pathIterative )
                || !comparePath( pathRecursive, pathAny ) ) {
            System.out.println("Error: Paths are not the same");
        }
        if ( !isValidPath( maze, pathRecursive ) ) {
            System.out.println("Error: Path is not valid");
        }

        if ( strategy == 'r' ) {
            return pathRecursive;
        } else if ( strategy == 'c' ) {
            return pathRecursiveCached;
        } else if ( strategy == 'i' ) {
            return pathIterative;
        } else if ( strategy == 'a' ) {
            return pathAny;
        } else {
            System.out.println("Unknown strategy");
            System.exit( 3 );
        }
        return new int[0][0];
    }

}
