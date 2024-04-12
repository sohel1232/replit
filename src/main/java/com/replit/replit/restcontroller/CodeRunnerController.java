package com.replit.replit.restcontroller;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/code-runner")
public class CodeRunnerController {

    @PostMapping("/python")
    public String executeCodePython(@RequestParam String code, @RequestParam String language, Model model) {
        System.out.println("Inside execute code with language: " + language);
        try {
            FileWriter writer = new FileWriter(  "env/main.py");
            writer.write(code);
            writer.close();

            String workingDir = System.getProperty("user.dir");

            ProcessBuilder buildProcess = new ProcessBuilder("docker", "build", "-t", "pc", "-f", "Dockerfile", workingDir);
            buildProcess.start().waitFor(); // Assuming build success, not capturing output
            System.out.println("building complete");


            // Run the image and capture output
            ProcessBuilder runProcess = new ProcessBuilder("docker", "run", "pc");
            runProcess.redirectOutput(ProcessBuilder.Redirect.PIPE); // Capture standard output
            Process run = runProcess.start();

            // Read output from the run process and store in a StringBuilder
            StringBuilder runOutput = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(run.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                runOutput.append(line).append("\n");
            }
            reader.close();

            int runExitValue = run.waitFor();

            String message;
            if (runExitValue == 0) {
                message = "Run successful. Run output:" + runOutput.toString();
            } else {
                message = "Run failed. Run output:" + runOutput.toString();
            }

            System.out.println("the message is clear.messgae: " + message);
            System.out.println("The output for the code is : " + runOutput);

            model.addAttribute("output" , runOutput);
            return runOutput.toString();
        } catch (IOException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("c++")
    public String executeCodeCPP(@RequestParam String code, @RequestParam String language, Model model){
        System.out.println("Inside execute code with language: " + language);
        try {
            FileWriter writer = new FileWriter(  "env/main.cpp");
            writer.write(code);
            writer.close();

            String workingDir = System.getProperty("user.dir");

            ProcessBuilder buildProcess = new ProcessBuilder("docker", "build", "-t", "pc", "-f", "DockerfileCPP", workingDir);
            buildProcess.start().waitFor(); // Assuming build success, not capturing output
            System.out.println("building complete");


            // Run the image and capture output
            ProcessBuilder runProcess = new ProcessBuilder("docker", "run", "pc");
            runProcess.redirectOutput(ProcessBuilder.Redirect.PIPE); // Capture standard output
            Process run = runProcess.start();

            // Read output from the run process and store in a StringBuilder
            StringBuilder runOutput = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(run.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                runOutput.append(line).append("\n");
            }
            reader.close();

            int runExitValue = run.waitFor();

            String message;
            if (runExitValue == 0) {
                message = "Run successful. Run output:" + runOutput.toString();
            } else {
                message = "Run failed. Run output:" + runOutput.toString();
            }

            System.out.println("the message is clear.messgae: " + message);
            System.out.println("The output for the code is : " + runOutput);

            model.addAttribute("output" , runOutput);
            return runOutput.toString();
        } catch (IOException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("java")
    public String executeCodeJava(@RequestParam String code, @RequestParam String language, Model model){
        System.out.println("Inside execute code with language: " + language);
        try {
            FileWriter writer = new FileWriter(  "env/Main.java");
            writer.write(code);
            writer.close();

            String workingDir = System.getProperty("user.dir");

            ProcessBuilder buildProcess = new ProcessBuilder("docker", "build", "-t", "pc", "-f", "DockerfileJava", workingDir);
            buildProcess.start().waitFor(); // Assuming build success, not capturing output
            System.out.println("building complete");


            // Run the image and capture output
            ProcessBuilder runProcess = new ProcessBuilder("docker", "run", "pc");
            runProcess.redirectOutput(ProcessBuilder.Redirect.PIPE); // Capture standard output
            Process run = runProcess.start();

            // Read output from the run process and store in a StringBuilder
            StringBuilder runOutput = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(run.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                runOutput.append(line).append("\n");
            }
            reader.close();

            int runExitValue = run.waitFor();

            String message;
            if (runExitValue == 0) {
                message = "Run successful. Run output:" + runOutput.toString();
            } else {
                message = "Run failed. Run output:" + runOutput.toString();
            }

            System.out.println("the message is clear.messgae: " + message);
            System.out.println("The output for the code is : " + runOutput);

            model.addAttribute("output" , runOutput);
            return runOutput.toString();
        } catch (IOException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
