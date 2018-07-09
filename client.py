import socket
import pygame
import sys
import time

host = "192.168.137.23"
port=5000
screen = pygame.display.set_mode((320,240),0)
f= open("log_cliente.txt","w+")
f.write("Inicio Recibe Conexion,Tiempo recepcion Imagen,Tiempo Mostrar Imagen\r\n")   

while True:    
    clientsocket=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    tiempo_1=int(round(time.time() * 1000)) #Conexion
    clientsocket.connect((host, port))
    tiempo_2=int(round(time.time() * 1000)) #Fin Conexion
    received = []
    # loop .recv, it returns empty string when done, then transmitted data is completely received
    while True:
        #print("esperando receber dado")
        recvd_data = clientsocket.recv(230400)
        if not recvd_data:
            break
        else:
            received.append(recvd_data)

    dataset = ''.join(received)
    tiempo_3=int(round(time.time() * 1000)) #Fin recibe
    image = pygame.image.fromstring(dataset,(320,240),"RGB") # convert received image from string
    screen.blit(image,(0,0)) # "show image" on the screen
    pygame.display.update()
    tiempo_4=int(round(time.time() * 1000)) #Fin despliega
    f.write("%d,%d,%d,%d\r\n" %(tiempo_1,tiempo_2,tiempo_3,tiempo_4))
    # check for quit events
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()
f.close() 