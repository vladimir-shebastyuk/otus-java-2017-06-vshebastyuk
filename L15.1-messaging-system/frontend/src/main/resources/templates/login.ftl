<#-- @ftlvariable name="login" type="java.lang.String" -->
<#-- @ftlvariable name="validationStatus" type="Boolean" -->
<#-- @ftlvariable name="validationMessage" type="java.lang.String" -->

<#include "base.ftl">
<#macro content>
<form action="/login" method="post">
    <#if !validationStatus>
        <div class="alert alert-danger" role="alert">
            ${validationMessage}
        </div>
    </#if>
    <div class="form-group">
        <label for="login">Login</label>
        <input type="text" class="form-control" name="login" code="exampleInputEmail1" placeholder="Login" value="${login}">
    </div>
    <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" name="password" code="password" placeholder="Password">
    </div>
    <button type="submit" class="btn btn-primary">Login</button>
</form>
</#macro>
<@display_base/>
