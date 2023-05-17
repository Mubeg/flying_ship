CXX = g++
CXXFLAGS = -fPIC -DDEBUG
CXX_INCLUDES = -I/usr/lib/jvm/java-11-openjdk-amd64/include -I/usr/lib/jvm/java-11-openjdk-amd64/include/linux -IJNI/flyingship/src/
BUILD_DIR = build/JNI/flyingship/src/
BACK_SRC_DIR = Backend/
SRC_DIR = JNI/flyingship/src/
WORKING_DIR = $(shell pwd)


libnative.so: clean java backend.o
	$(CXX) -shared $(CXXFLAGS) $(CXX_INCLUDES) -o $(BUILD_DIR)libnative.so $(SRC_DIR)messenger.cpp $(BUILD_DIR)*.o

backend.o:
	$(CXX) $(CXX_INCLUDES) $(CXXFLAGS) -c $(BACK_SRC_DIR)*.cpp
	mv *.o ${BUILD_DIR}

java:
	mkdir build
	javac -cp "${WORKING_DIR}/lib/gson.jar:." -h $(SRC_DIR)headers -d build $(SRC_DIR)*.java
	cp -r GUI/img ./

clean:
	rm -rf ./img
	rm -rf build
	rm -f *.o


run:
	bash FlyingShip.sh
