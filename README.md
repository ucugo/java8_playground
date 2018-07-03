 __Installation Doc:__

> This application uses MAVEN for building the executable and requires a maven installation in its build environment.


__Build and create a jar file:__

First navigate to project root ({path}/PlayGround/) from your command line.
Type and enter cmd: mvn clean install (This will first run the Unit tests and then create a jar file 'zopa-test-app.jar' in the target directory)

__Run application:__

copy the generated zopa-test-app.jar created in the build steps to a directory of your choice. And from that directory run command with required arguments:

java -jar zopa-test-app.jar  {Absolute path to market data file} {Loan Amount}

> Path to market data file must exists. See sample in PlayGround/src/test/resources/data/market_data.csv




