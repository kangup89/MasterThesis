// Connecting to ROS
// -----------------
var ros = new ROSLIB.Ros();

// Create a connection to the rosbridge WebSocket server.
//ros.connect('ws://localhost:9090');
//ros.connect('ws://a0493699.ngrok.io');
//ros.connect('ws://192.168.48.128:9090')

ros.connect('ws://kangup.eu.ngrok.io');

// If there is an error on the backend, an 'error' emit will be emitted.
ros.on('error', function(error) {
  document.getElementById('connecting').style.display = 'none';
  document.getElementById('connected').style.display = 'none';
  document.getElementById('closed').style.display = 'none';
  document.getElementById('error').style.display = 'inline';
  console.log(error);
});

// Find out exactly when we made a connection.
ros.on('connection', function() {
  console.log('Connection made!');
  document.getElementById('connecting').style.display = 'none';
  document.getElementById('error').style.display = 'none';
  document.getElementById('closed').style.display = 'none';
  document.getElementById('connected').style.display = 'inline';
});

ros.on('close', function() {
  console.log('Connection closed.');
  document.getElementById('connecting').style.display = 'none';
  document.getElementById('connected').style.display = 'none';
  document.getElementById('closed').style.display = 'inline';
});

/*var btn_forward = document.getElementById('btn_forward'),
    btn_left = document.getElementById('btn_left'),
    btn_right = document.getElementById('btn_right'),
    btn_stop = document.getElementById('btn_stop'),
    btn_reverse = document.getElementById('btn_reverse');*/

// First, we create a Topic object with details of the topic's name and message type.
var cmdVel = new ROSLIB.Topic({
  ros : ros,
  name : '/cmd_vel_mux/input/teleop',
  messageType : 'geometry_msgs/Twist'
});

// Then we create the payload to be published. The object we pass in to ros.Message matches the
// fields defined in the geometry_msgs/Twist.msg definition.
var twist = new ROSLIB.Message({
  linear : {
    x : 0,
    y : 0,
    z : 0
  },
  angular : {
    x : 0,
    y : 0,
    z : 0
  }
});

var timer = null;
var elements = document.getElementsByClassName('button');

function move(evt){
  if(evt == 'forward'){
    mouseUp();
    twist.linear.x = 0.25;
    btn_forward.parentElement.className += " active";
    timer = setInterval(function(){
      //console.log("move forward");
      cmdVel.publish(twist);
    }, 100);
  }else if(evt == 'left'){
    mouseUp();
    twist.angular.z = 0.75;
    //twist.linear.x = 0.25;
    btn_left.parentElement.className += " active";
    timer = setInterval(function(){
      //console.log("move left");
      cmdVel.publish(twist);
    }, 100);
  }else if(evt == 'right'){
    mouseUp();
    twist.angular.z = -0.75;
    //twist.linear.x = 0.25;
    btn_right.parentElement.className += " active";
    timer = setInterval(function(){
      //console.log("move right");
      cmdVel.publish(twist);
    }, 100);
  }else if(evt == 'stop'){
    mouseUp();
    twist.angular.z = 0;
    twist.linear.x = 0;
    btn_stop.parentElement.className += " active";
    mouseUp();
    /*timer = setInterval(function(){
      //console.log("stop");
      cmdVel.publish(twist);
    }, 100);*/
  }else if(evt == 'reverse'){
    mouseUp();
    twist.linear.x = -0.2;
    btn_reverse.parentElement.className += " active";
    timer = setInterval(function(){
      //console.log("move reverse");
      cmdVel.publish(twist);
    }, 100);
  }
}

document.onkeydown = function(e) {
    switch (e.keyCode) {
      case 37:
        if(timer == null) {
          //console.log("press left");
          move("left");
        }
        break;
      case 38:
        if(timer == null) {
          //console.log("press forward");
          move("forward");
        }
        break;
      case 39:
        if(timer == null) {
          //console.log("press right");
          move("right");
        }
        break;
      case 40:
        if(timer == null) {
          //console.log("press reverse");
          move("reverse");
        }
        break;
    }
};

document.onkeyup = function(){
  clearInterval(timer);
  timer = null;
  //console.log('key up!');
  twist.linear.x = twist.linear.y = twist.angular.z = 0;

  for (var i = 0; i < elements.length; i++) {
    elements[i].className = "button";
    //console.log("deactive!");
  }
}

function mouseUp(){
  clearInterval(timer);
  timer = null;
  //console.log('mouse is up!');
  twist.linear.x = twist.linear.y = twist.angular.z = 0;

  for (var i = 0; i < elements.length; i++) {
    elements[i].className = "button";
    //console.log("deactive!");
  }
}

var myApp = angular.module('myApp', ['ngRoute']).constant('ROS2D', window.ROS2D);

myApp.config(function($routeProvider){
  $routeProvider
      .when('/', {
        templateUrl : 'pages/video.html',
        controller : 'videoController'
      })
      .when('/video', {
        templateUrl : 'pages/video.html',
        controller : 'videoController'
      })
      .when('/map', {
        templateUrl : 'pages/costmap.html',
        controller : 'costmapController'
      })
      .when('/costmap', {
        templateUrl : 'pages/costmap.html',
        controller : 'costmapController'
      })
});

myApp.controller('videoController', function($scope){


  var sub_image = new ROSLIB.Topic({
    ros : ros,
    name : '/camera/rgb/image_raw/compressed',
    messageType : 'sensor_msgs/CompressedImage'
  });

  sub_image.subscribe(function(message){
    //console.log('subscribe!');
    var imageData = "data:image/jpeg;base64," + message.data;
    var style = "width:" + window.innerWidth + "px;height:" + window.innerHeight + "px;";
    var image_element = document.getElementById('ros_image');
    if(image_element != null){
      image_element.setAttribute('src', imageData);
      document.getElementById('ros_image').setAttribute('style', style);
    }

    //console.log(imageData);
  })

  // And finally, publish.
  cmdVel.publish(twist);
});

myApp.controller('mapController', function($scope){
  // Create the main viewer.
  var viewer = new ROS2D.Viewer({
    divID : 'map',
    width : window.innerWidth,
    height : window.innerHeight
  });


  // Setup the nav client.
  var nav = NAV2D.OccupancyGridClientNav({
    ros : ros,
    rootObject : viewer.scene,
    viewer : viewer,
    continuous : true,
    serverName : '/move_base',
    actionName : '/move_base_msgs/MoveBaseAction',
    withOrientation : true,
    costmap : false
  });

});

myApp.controller('costmapController', function($scope){
  // Create the main viewer.
  var viewer2 = new ROS2D.Viewer({
    divID : 'map2',
    width : window.innerWidth,
    height : window.innerHeight
  });

  // Setup the nav client.
  var nav2 = NAV2D.OccupancyGridClientNav({
    ros : ros,
    rootObject : viewer2.scene,
    viewer : viewer2,
    continuous : true,
    serverName : '/move_base',
    actionName : '/move_base_msgs/MoveBaseAction',
    costmap : '/move_base/local_costmap/costmap',
    withOrientation : true
  });

  //console.log("this_width"  + NAV2D.base_width);
  //console.log("this_height"  + NAV2D.base_height);

  // Create the main viewer.
  var viewer3 = new ROS2D2.CostMapViewer({
    divID : 'costmap',
    width : window.innerWidth,
    height : window.innerHeight
  });

  // Setup the nav client.
  var nav3 = NAV2D2.CostMapClientNav({
    ros : ros,
    rootObject : viewer3.scene,
    viewer : viewer3,
    continuous : true,
    topic : '/move_base/local_costmap/costmap',
    serverName : '/move_base',
    actionName : '/move_base_msgs/MoveBaseAction',
    withOrientation : true,
    costmap : true
  });

});
