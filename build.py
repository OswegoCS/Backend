import os

os.system("mvn package")
os.system("java -jar target/codepeerreview-2.0.jar")