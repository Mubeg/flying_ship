start:
	make clean
	mkdir build
	javac -h flyingship/src/headers -d build flyingship/src/*.java
	g++ -I/usr/lib/jvm/java-11-openjdk-amd64/include -I/usr/lib/jvm/java-11-openjdk-amd64/include/linux -o build/flyingship/src/libnativeprint.so -shared flyingship/src/connector.cpp
clean:
	rm -rf build
run:
	bash FlyingShip.sh