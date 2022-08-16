Build Version : #version#<br/>
Deploy Date: #deployDate#<br/>
Environment:
<g:if env="development">
    Development
</g:if>
<g:if env="test">
    UAT
</g:if>
<g:if env="production">
    Production
</g:if>