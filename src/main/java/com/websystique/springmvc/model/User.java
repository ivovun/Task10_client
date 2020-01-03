package com.websystique.springmvc.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
    private Long id;

    private String ssoId;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private Set<UserProfile> userProfiles = new HashSet<>();

    private String rolesDescription;

    public String getRolesDescription() {
        return rolesDescription();
    }

    public void setRolesDescription(String rolesDescription) {
        this.rolesDescription = rolesDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userProfiles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return ssoId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    public String rolesDescription() {
        return userProfiles.stream().map(c -> c.toString().toLowerCase()).collect(Collectors.joining(", "));
    }

    public String roleEnabled(Long roleId) {
        return userProfiles.stream().map(c -> c.getId().equals(roleId)).anyMatch(Boolean::booleanValue) ? "THIS_ROLE_IS_ENABLED" : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return ssoId.equals(user.ssoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ssoId);
    }

    /*
     * DO-NOT-INCLUDE passwords in toString function.
     * It is done here just for convenience purpose.
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", ssoId=" + ssoId + ", password=" + password
                + ", firstName=" + firstName + ", lastName=" + lastName
                + ", email=" + email + "]";
    }


}
