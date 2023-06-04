# XO-Game-Developed-on-Java

TO RUN using netbeans: 
    1- Import the project into netbeans.
    2- Start the server by running file "XOServer.java".
    3- Initiate two players by running "XOClient.java" twice.
    4- Switch between tabs to simulate playing between two players.

To run using command window:
    A- Compile
        * Open "src/" folder and from there run these command:
            1- javac utils/*
            2- javac client/*
            3- javac server/*
    B- run
        1- Start a command window and Initiate server using this command:
            java server/XOServer
        2- Start two command windows and Initiate clients using this command:
            java client/XOClient
