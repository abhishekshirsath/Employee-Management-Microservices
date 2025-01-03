package net.javaguides.organizationservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.javaguides.organizationservice.DTO.OrganizationDTO;
import net.javaguides.organizationservice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "OrganizationController",
        description = "Organization Controller exposes REST APIs for Organization-Service"
)
@RestController
@RequestMapping("api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Operation(
            summary = "Save Organization REST API",
            description = "Save Organization REST API is used to save organization object in DB"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    // Build saved Org REST API
    @PostMapping
    public ResponseEntity<OrganizationDTO> saveOrganization(@RequestBody OrganizationDTO organizationDTO){
        OrganizationDTO savedOrganizationDTO = organizationService.saveOrganization(organizationDTO);
        return new ResponseEntity<>(savedOrganizationDTO, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Organization REST API",
            description = "Get Organization REST API is used to get organization object from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("{organizationCode}")
    public ResponseEntity<OrganizationDTO> getOrganizationByCode(@PathVariable("organizationCode") String organizationCode){
        OrganizationDTO organizationDTO = organizationService.getOrganizationByCode(organizationCode);
        return  new ResponseEntity<>(organizationDTO,HttpStatus.OK);
    }



}
