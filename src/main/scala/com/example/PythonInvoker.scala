package com.example

import sys.process._

class PythonInvoker{
    def callPython = {
        val pythonProjectPath = "/home/himel/Documents/akka-playground/src/main/python" 
        val pythonEnvPath = s"${pythonProjectPath}/venv/bin"    
           
//        val result = s"${pythonEnvPath}/python3 ${pythonProjectPath}/sample.py" ! ProcessLogger(stdout append _, stderr append _)
//        println(result)
        var normalLines: String = ""
        var errorLines: String = ""
        val processLogger = ProcessLogger(line => normalLines += line+'\n', line => errorLines += line+'\n')
        val exitStatus = s"${pythonEnvPath}/python3 ${pythonProjectPath}/svd-test.py" ! processLogger
        if(exitStatus == 0){
            println(s"Succussfully run process")
            println("Outputs: ")
            println(normalLines)
            println("Errors: ")
            println(errorLines)
        }else {
            println(s"Error:${exitStatus} running the process")
        }
    }
}