CXX = g++
CXXFLAGS = -shared -fPIC -DDEBUG
CXX_INCLUDES = -I/usr/lib/jvm/java-11-openjdk-amd64/include -I/usr/lib/jvm/java-11-openjdk-amd64/include/linux -IJNI/flyingship/src/
BUILD_DIR = build/JNI/flyingship/src/
BACK_SRC_DIR = Backend/
SRC_DIR = JNI/flyingship/src/


libnative.so: clean java backend.o
	$(CXX) $(CXXFLAGS) $(CXX_INCLUDES) -o $(BUILD_DIR)libnative.so $(SRC_DIR)messenger.cpp $(BUILD_DIR)*.o

backend.o:
	$(CXX) $(CXX_INCLUDES) -c -fPIC $(BACK_SRC_DIR)*.cpp
	mv *.o ${BUILD_DIR}

java:
	mkdir build
	javac -h $(SRC_DIR)headers -d build $(SRC_DIR)*.java

clean:
	rm -rf build
	rm -f *.o


run:
	bash FlyingShip.sh
