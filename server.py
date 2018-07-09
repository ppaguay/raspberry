import socket
import pygame
import pygame.camera
import sys
import time


port = 5000
pygame.init()
f= open("log_server.txt","w+")
f.write("%s,%s,%s\r\n" %('Inicio','FCI,ITx','Fin Tx'))    

serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serversocket.bind(("",port))
serversocket.listen(1)

pygame.camera.init()
webcam = pygame.camera.Camera("/dev/video0",(320,240))
webcam.start()


while True:

        connection, address = serversocket.accept()
        tiempo_1=int(round(time.time() * 1000)) #Inicio captura imagen
        image = webcam.get_image() # capture image
        data = pygame.image.tostring(image,"RGB") # convert captured image to string, use RGB color scheme
        tiempo_2=int(round(time.time() * 1000)) #Fin captura imagen  inicio envio
        connection.sendall(data)
        tiempo_3=int(round(time.time() * 1000)) #Fin envio
        f.write("%d,%d,%d\r\n" %(tiempo_1,tiempo_2,tiempo_3))
     	
        time.sleep(0.1)
        connection.close()

f.close() 