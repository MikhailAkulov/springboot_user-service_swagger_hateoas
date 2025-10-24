package com.akulov.springboot.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Schema(description = "Объект передачи пользовательских данных")
public class UserDto extends RepresentationModel<UserDto> {

    @Schema(description = "Уникальный идентификатор пользователя", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Не может быть пустым, или содержать только пробелы")
    @Size(min = 2, max = 25, message = "Имя должно быть длиной от 2 до 25 символов")
    @Schema(description = "Имя пользователя", example = "John Connor", minLength = 2, maxLength = 25)
    private String name;

    @Email(message = "Адрес электронной почты должен быть действительным")
    @NotBlank(message = "Не может быть пустым, или содержать только пробелы")
    @Schema(description = "Адрес электронной почты пользователя", example = "john@mail.com")
    private String email;

    @Min(value = 1, message = "Возраст должен быть не менее 1")
    @Max(value = 110, message = "Возраст не должен превышать 110")
    @Schema(description = "Возраст пользователя", example = "42", minimum = "1", maximum = "110")
    private Integer age;

    @Schema(description = "Время регистрации пользователя", example = "2025-06-24T15:30:00",
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
