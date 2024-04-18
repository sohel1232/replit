package com.replit.replit.restcontroller;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.replit.replit.entity.Replit;
import com.replit.replit.repository.ReplitRepository;
import com.replit.replit.service.ReplitService;
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
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/code-runner")
public class CodeRunnerController {

     ReplitService replitService;

    public CodeRunnerController(ReplitService replitService) {
        this.replitService = replitService;
    }

    @PostMapping("/python")
    public String executeCodePython(@RequestParam String code,
                                    @RequestParam String language,
                                    @RequestParam String replitName,
                                    Model model) {
        System.out.println("Inside execute code with language: " + language);
        try {
            Replit replit = replitService.findByName(replitName);
            replit.setCode(code);
            replit.setUpdatedAt(LocalDateTime.now());
            System.out.println("Inside api. replit found with name : " + replit);
            FileWriter writer = new FileWriter(  "env/main.py");
            writer.write(code);
            writer.close();

            String workingDir = System.getProperty("user.dir");

            ProcessBuilder buildProcess = new ProcessBuilder("docker", "build", "-t", "pc", "-f", "DockerfilePython", workingDir);
            buildProcess.start().waitFor(); // Assuming build success, not capturing output
            System.out.println("building complete");


            // Run the image and capture output
            ProcessBuilder runProcess = new ProcessBuilder("docker", "run", "--name", "pc_container", "pc");
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
            
            ProcessBuilder stopProcess = new ProcessBuilder("docker", "stop", "pc_container");
            stopProcess.start().waitFor(); // Assuming success, not capturing output
            ProcessBuilder removeProcess = new ProcessBuilder("docker", "rm", "pc_container");
            removeProcess.start().waitFor(); // Assuming success, not capturing output
            
            ProcessBuilder deleteImageProcess = new ProcessBuilder("docker", "rmi", "pc");
            deleteImageProcess.start().waitFor(); // Assuming deletion success, not capturing output
            System.out.println("Image deletion complete");

            replitService.save(replit);
            return runOutput.toString();
        } catch (IOException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("cpp")
    public String executeCodeCPP(@RequestParam String code, @RequestParam String language, Model model,@RequestParam String replitName){
        System.out.println("Inside execute code with language: " + language);
        try {
            System.out.println("Inside cpp execution");
            Replit replit = replitService.findByName(replitName);
            replit.setCode(code);
            replit.setUpdatedAt(LocalDateTime.now());
            System.out.println("Inside api. replit found with name : " + replit);
            FileWriter writer = new FileWriter(  "env/main.cpp");
            writer.write(code);
            writer.close();
            System.out.println("file wiritng done");

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
            replitService.save(replit);
            return runOutput.toString();
        } catch (IOException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("java")
    public String executeCodeJava(@RequestParam String code, @RequestParam String language, Model model,@RequestParam String replitName){
        System.out.println("Inside execute code with language: " + language);
        try {
            Replit replit = replitService.findByName(replitName);
            replit.setCode(code);
            replit.setUpdatedAt(LocalDateTime.now());
            System.out.println("Inside api. replit found with name : " + replit);
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

            ProcessBuilder stopProcess = new ProcessBuilder("docker", "stop", "pc_container");
            stopProcess.start().waitFor(); // Assuming success, not capturing output
            ProcessBuilder removeProcess = new ProcessBuilder("docker", "rm", "pc_container");
            removeProcess.start().waitFor(); // Assuming success, not capturing output

            ProcessBuilder deleteImageProcess = new ProcessBuilder("docker", "rmi", "pc");
            deleteImageProcess.start().waitFor(); // Assuming deletion success, not capturing output
            System.out.println("Image deletion complete");
            replitService.save(replit);
            return runOutput.toString();
        } catch (IOException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
