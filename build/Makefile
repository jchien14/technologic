JFLAGS = -cp
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) "../src/main/resources/guava-18.0.jar" -d ./ -sourcepath ../src/main/java $*.java

CLASSES = \
		../src/main/java/encryption/MurmurHash3.java \
		../src/main/java/encryption/EncryptUtils.java \
		../src/main/java/history/HistoryUtils.java \
		../src/main/java/history/PersonHistory.java \
		../src/main/java/history/GalleryState.java \
		../src/main/java/history/HistoryPrinter.java \
		../src/main/java/main/LogRead.java \
		../src/main/java/main/LogAppend.java

default: classes
		jar cfe logread.jar main.LogRead */*.class; jar cfe logappend.jar main.LogAppend */*.class

classes: $(CLASSES:.java=.class)

clean:
		$(RM) -r encryption history *.jar
