## Security Overview

- **AuthN/Z**: JWT (HS256), role-based access control
- **Transport**: HTTPS everywhere, HSTS
- **Data**: At-rest encryption via managed DB; secrets in env vars
- **Input Validation**: Backend DTO validation + OWASP rules
- **Rate Limiting**: Per-IP and per-tenant
- **Audit Logs**: Auth events, data exports, administrative actions
- **Secrets**: Rotated quarterly; least privilege for cloud IAM
- **Vulnerability Mgmt**: Weekly dependency scans; monthly patch window

Compliance roadmap: GDPR basics (DPA, DSAR), SOC2 (controls inventory), logging retention 90 days.
