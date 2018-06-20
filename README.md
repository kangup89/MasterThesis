
This thesis describes development of an android application to do health monitoring of the
elderly person and remotely controls a remote robot “TurtleBot” in order to allow caregivers
to interact with elderly person in more convenient way.

A hybrid android application has been developed. The main services of the application are
divided into two: i) Health Monitoring System which provides caregivers with elderly
person's physiological data collected by various sensors installed in or around the house, and
ii) Robot Telepresence System which controls the robot by using android application and
provides video chat with elderly person.

The application is connected with a database server and is able to receive patient's
physiological data by using HTTP Request. In order to remotely control the "TurtleBot", ROS
(Robot Operating System) has been used. The video chat service is implemented by using
WebRTC technology, which enables peer to peer connection between the application and the
TurtleBot laptop.

The developed application is tested by using various performance testing tools. The results
show that the application has relatively high performance. In this thesis, we have reached our
goal to develop an android application with essential services we have decided, but the
application still needs some improvements to be used in the real world.






The application is designed as combination of 4 parts which are developed by different technologies: 

        web application server part (Java, Spring Framework, Maven, Apache Tomcat)
        
        android part (Java, Cordova)
        
        robot controlling part (JavaScript, HTML, CSS, ROSLIB, Angular)
        
        videoChat part (JavaScript, HTML, CSS, WebRTC, QuickBlox)


You can find more detailed describtion in "kangul_Master_Thesis.pdf"
        
        