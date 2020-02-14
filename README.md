IMS
This project shows the skills I have gained during the five weeks I have been a trainee at the QA Academy. The project is an inventory management system (IMS) written in Java 8 that communicates with a MySQL database. Users can perform basic CRUD functionality on database items in five different tables. The system accepts orders, which include item IDs and customers IDs, and calculates a total price of the order which then can then be read from the database later.
Getting Started
I have provided instructions that will get you a copy of my project up and running on your local install for development and testing purposes. Make sure that you have the following installed below.
git clone https://github.com/malikbensalem/IMSV2 
cd [folder that has just been cloned]
Prerequisites
Things needed to install the software:
Maven
Java 8 JDK +
IDE for development (e.g. IntelliJ)
Git for versioning
Installing (using bash terminal)
1.Clone project as defined above
2.cd [cloned project name] 
3.mvn java -jar 	This will make an executable .jar file that JVM can run.
4.Open IMS in an IDE (e.g IntelliJ).
Unit Tests
These are test that test a single method without mocking or running any other methods for example:
public int add (int num,int num2){
return num+num2;
} 
This method above will be tested using a unit test
@Test
public void addTest(){
assertEquals(5,add(4,1));
}
(this test will pass as 4+1 is 5)The method above is a unit test as it test for an expected value and the actual value “assertEquals(expected,actual)”
Integration Tests
These test methods that call other methods to complete, however we use mockito to stub (fake) an input so it doesn’t need to call the method to complete its task. The reason to mock a method call is to find and isolate a problem to single method. An example of Intergration would be:
public boolean a(){
return b();
}
Above method will tested
public boolean b(){
return false;
}
Above method will be stubbed
@spy
B b;
@Test
Public void aTest(){
Mockito.when(b.b()).thenReturn(true);
assertEquals(true,a());
}
Above method will test a()
Coding style tests
This can be done by uploading the project to SonarQube. This server will analyse the code and generate different things, such as code smells and bugs (that IDEs such as eclipse doesn’t tell the developer). 
Deployment
mvn deploy
Built With
Maven - Dependency Management
Versioning
I used SemVer for versioning. 
Authors
Malik Bensalem
License
This project is licensed under the MIT license - see the LICENSE.md file for details
