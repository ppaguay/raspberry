def main():
     f= open("log.txt","w+")
     cont=1
     f.write("col1,col2,col3\r\n")     
     f.write("%d,%d,%d\r\n" %(2,2,2))
     f.close()   
     
if __name__== "__main__":
  main()