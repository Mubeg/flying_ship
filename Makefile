CXX = g++
CXXFLAGS = -shared -fPIC -DDEBUG
CXX_INCLUDES = -I/usr/lib/jvm/java-11-openjdk-amd64/include -I/usr/lib/jvm/java-11-openjdk-amd64/include/linux
BUILD_DIR = build/JNI/flyingship/src/
SRC_DIR = JNI/flyingship/src/


libnative.so: clean java backend.o
	$(CXX) $(CXXFLAGS) $(CXX_INCLUDES) -o $(BUILD_DIR)libnative.so $(SRC_DIR)messenger.cpp $(BUILD_DIR)backend.o

backend.o:
	$(CXX) $(CXX_INCLUDES) -c -o $(BUILD_DIR)backend.o $(SRC_DIR)backend.cpp

java:
	mkdir build
	javac -h $(SRC_DIR)headers -d build $(SRC_DIR)*.java

clean:
	rm -rf build 


run:
	bash FlyingShip.sh
