from datetime import datetime
import time
ahora = datetime.now()  # Obtiene fecha y hora actual

print("Microsegundos:",ahora.microsecond)  # Muestra microsegundo

milli_sec = int(round(time.time() * 1000))
print(milli_sec)