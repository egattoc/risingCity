JFLAGS = -g
JC = javac -cp  risingCity.java BuildingNode.java RBTree.java RBNode.java MinHeap.java 
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
    risingCity.java \
	BuildingNode.java \
	RBTree.java \
	RBNode.java \
	MinHeap.java
        

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *