compile: Constants.java GopherServer.java Main.java
	cd src/Gopher/Server
	javac .*java
	cd ../../..
serve: compile
	java -cp src Gopher.Server.Main
