package com.infotech.controller;

import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emp")
public class EmpController {

    // hasRole, hasAnyRole, hasAuthority, hasAnyAuthority
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINETRAINEE')")
    public String getAllEmp() {
        System.out.println("All Emp");
        return "All emp";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('student:write')")
    public String createEmp(@RequestBody Emp emp) {
        System.out.println("Create emp");
        return "create emp";
    }


    @PutMapping("/update")
    @PreAuthorize("hasAuthority('student:write')")
    public String updateEmp(){
        return "emp updated";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('student:write')")
    public String delete() {
        return "emp deleted";
    }

}

@Data
class Emp {
    private int id;
    private String name;
}
