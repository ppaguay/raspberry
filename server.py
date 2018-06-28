import socket
import pygame
import pygame.camera
import sys
import time

from datetime import datetime

port = 5000
pygame.init()
ahora = datetime.now()
f= open("log.txt","w+")
f.write("tiempo1,tiempo2,tiempo4,tiempo4,tiempo5\r\n")     
   

serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serversocket.bind(("",port))
serversocket.listen(1)


tiempo_1=ahora.microsecond #Inicio encendido de camara
pygame.camera.init()
webcam = pygame.camera.Camera("/dev/video0",(320,240))
webcam.start()
tiempo_2=ahora.microsecond  #Fin encedido de camara
while True:

        connection, address = serversocket.accept()
        tiempo_3=ahora.microsecond #Inicio captura imagen
        image = webcam.get_image() # capture image
        data = pygame.image.tostring(image,"RGB") # convert captured image to string, use RGB color scheme
        tiempo_4=ahora.microsecond #Fin captura imagen  inicio envio
        connection.sendall(data)
        tiempo_5=ahora.microsecond #Fin envio
        f.write("%d,%d,%d,%d,%d\r\n" %(tiempo_1,tiempo_2,tiempo_3,tiempo_4,tiempo_5))
     	f.close() 
        time.sleep(0.1)
        connection.close()