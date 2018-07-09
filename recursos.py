import psutil
def pids_active(pids_computer):
    """
    This function find pids of computer and return the valid.
    """
    pid_valid = {}
    for pid in pids_computer:
        data = None
        try:
            process = psutil.Process(pid)
            data = {"pid": process.pid,
                    "status": process.status(),
                    "percent_cpu_used": process.cpu_percent(interval=0.0),
                    "percent_memory_used": process.memory_percent()}

        except (psutil.ZombieProcess, psutil.AccessDenied, psutil.NoSuchProcess):
            data = None

        if data is not None:
            pid_valid[process.name()] = data
    return pid_valid 
pids_active("8844")