# capture-server
Captures images from local image device and makes it available as a REST endpoint.

Simulated a network camera, sitll image part. Useful for developing applications capturing images from network cameras.
 
Currently limited to macosx, but it may work on Windows and Linux too if you add the corresponding windows and linux
opencv-dependencies in pom.xml.

## Release notes
* 2016-04-12
  * First version

## Usage
Start the app. Get image built in camera using URL http://localhost:9016/image
