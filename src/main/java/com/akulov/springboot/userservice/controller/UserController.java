package com.akulov.springboot.userservice.controller;

import com.akulov.springboot.userservice.dto.UserDto;
import com.akulov.springboot.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api")
@Tag(name = "User service", description = "REST API для учёта пользователей")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "get all users", description = "Получить список всех пользователей, зарегистрированных в системе")
    @ApiResponse(responseCode = "200", description = "The list of all users was retrieved successfully")
    @GetMapping("/users")
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> showAllUsers() {
        List<EntityModel<UserDto>> allUsers = userService.findAllUsers().stream()
                .map(userDto -> EntityModel.of(userDto,
                        linkTo(methodOn(UserController.class).getUser(userDto.getId())).withSelfRel()
                                .andAffordance(afford(methodOn(UserController.class).updateUser(userDto.getId(), null)))
                                .andAffordance(afford(methodOn(UserController.class).deleteUser(userDto.getId()))),
                        linkTo(methodOn(UserController.class).showAllUsers()).withRel("allUsers")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(allUsers)
                .add(linkTo(methodOn(UserController.class).showAllUsers()).withSelfRel())
                .add(linkTo(methodOn(UserController.class).createUser(null)).withRel("createUser")));
    }

    @Operation(summary = "get user by id", description = "Получить информацию об определённом пользователе по его id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "404", description = "User with specified ID not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<UserDto>> getUser(
            @Parameter(description = "The ID of the user being searched for")
            @PathVariable Long id) {
        try {
            UserDto userDto = userService.findUserById(id);
            return ResponseEntity.ok(this.DtoToEntityModel(userDto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "add new user", description = "Добавить нового пользователя в систему")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User added successfully"),
            @ApiResponse(responseCode = "400", description = "Adding user failed. Invalid input data",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping("/users")
    public ResponseEntity<EntityModel<UserDto>> createUser(
            @Parameter(description = "User's data for creation")
            @Valid @RequestBody UserDto userDto) {
        UserDto newUserDto = userService.addUser(userDto);
        EntityModel<UserDto> entityModel = DtoToEntityModel(newUserDto);
        return ResponseEntity
                .created(URI.create("/api/users/" + newUserDto.getId()))
                .body(entityModel);
    }

    @Operation(summary = "update user", description = "Обновить данные определённого пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User with specified ID not found",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Failed to update user. Invalid input data",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<EntityModel<UserDto>> updateUser(
            @Parameter(description = "ID of the user being updated")
            @PathVariable Long id,
            @Parameter(description = "New user data")
            @Valid @RequestBody UserDto userDto) {
        try {
            UserDto updatedUserDto = userService.updateUser(id, userDto);
            return ResponseEntity.ok(this.DtoToEntityModel(updatedUserDto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "delete user by id", description = "Удалить из системы определённого пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User with specified ID not found")
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user being deleted")
            @PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "get list users by name", description = "Получить список пользователей по имени")
    @GetMapping("/users/name/{name}")
    public CollectionModel<EntityModel<UserDto>> showAllUsersByName(@PathVariable String name) {
        List<EntityModel<UserDto>> allUsers = userService.findAllUsersByName(name).stream()
                .map(this::DtoToEntityModel)
                .collect(Collectors.toList());

        return CollectionModel.of(allUsers)
                .addIf(!allUsers.isEmpty(),
                        () -> linkTo(methodOn(UserController.class).showAllUsersByName(name)).withSelfRel())
                .add(linkTo(methodOn(UserController.class).showAllUsers()).withRel("allUsers"))
                .add(linkTo(methodOn(UserController.class).createUser(null)).withRel("createUser"));
    }

    private EntityModel<UserDto> DtoToEntityModel(UserDto userDto) {
        return EntityModel.of(userDto)
                .add(linkTo(methodOn(UserController.class).getUser(userDto.getId())).withSelfRel())
                .add(linkTo(methodOn(UserController.class).showAllUsers()).withRel("allUsers"))
                .add(linkTo(methodOn(UserController.class).updateUser(userDto.getId(), null)).withRel("updateUser"))
                .add(linkTo(methodOn(UserController.class).deleteUser(userDto.getId())).withRel("deleteUser"));
    }
}