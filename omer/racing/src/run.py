import subprocess
import os.path
import simplejson as json
import sys

def execute_output(command, arguments):
    proc = subprocess.Popen([command, arguments],
                            stdout=subprocess.PIPE,
                            shell=True)
    return proc.communicate()

    
def execute(command):
    proc = subprocess.Popen([command, ""],
                            stdout=subprocess.PIPE,
                            shell=True)
    return os.waitpid(proc.pid, 0)[1]


##Assuming we are in a directory with 'pack'.

NoError = 0
CompileError = -1
LinkError = -2



def compila():
    f1 = "comp.out"
    f2 = "comp.err"
    execute("g++ -Wall proxycar.cc -c >%s 2>%s" % (f1,f2))
    if os.path.exists(f1):
        if os.path.getsize(f1)>0: return (CompileError, f1)
        os.unlink(f1)
    if os.path.exists(f2):
        if os.path.getsize(f2)>0: return (CompileError, f2)
        os.unlink(f2)

    f1 = "link.out"
    f2 = "link.err"
    execute("g++ mainsim.o corbes.o monocar.o proxycar.o >%s 2>%s" %
            (f1,f2))
    if os.path.exists(f1):
        if os.path.getsize(f1)>0: return (LinkError, f1)
        os.unlink(f1)

    if os.path.exists(f2):
        if os.path.getsize(f2)>0: return (LinkError, f2)
        os.unlink(f2)

    return (NoError, "")

def corre(circuit_filename):
    f = "trace.out"
    if os.path.exists(f): os.unlink(f)
    execute("./a.out %s >%s" % (circuit_filename, f))
    return (NoError, "")    
 

res = compila()
if res[0]==0: res = corre("circuit.txt")

if res[0]<0:
    print json.dumps( {"code": res[0], "message": res[1]} )
else:
    print json.dumps( {"code": 0, "message": "Ok"} )
