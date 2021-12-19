function main(){
    startApplication("javawebserver-1.0-SNAPSHOT.jar");
    //Click button
    clickButton(waitForObject(names.startServerJButton));
    test.compare(waitForObjectExists(name.outputJLabel),"SERVER STATE IS: STARTED - WORKING");

    clickButton(waitForObject(names.maintainServerJButton));
    test.compare(waitForObjectExists(name.outputJLabel),"SERVER STATE IS: MAINTAINED");

    clickButton(waitForObject(names.stopServerJButton));
    test.compare(waitForObjectExists(names.outputJLabel),"SERVER STATE IS: STOP");
}