compile:
	cd src/Gopher/Server && javac *.java
serve:
	java -cp src Gopher.Server.Main
