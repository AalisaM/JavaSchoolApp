<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Sending Email with Freemarker HTML Template Example</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <!-- use the font -->
    <style>
        body {
            font-family: 'Tahoma', sans-serif;
            font-size: 48px;
        }
    </style>
</head>
<body style="margin: 0; padding: 0;">

<table align="center" border="0" cellpadding="0" cellspacing="0" width="800" style="border-collapse: collapse;">
    <tr>
        <td align="center" bgcolor="#e20074">
            <h1 style="color: white">T-Store</h1>
        </td>
    </tr>
    <tr>
        <td bgcolor="#FDFCFC" style="padding: 40px 30px 40px 30px;">
            <p>Dear ${fullName},</p>
            <p>We are pleased to inform that you have been joined to Online Store of Board Games. </p>
            <p>Please find your credentials below. </p>
            <p>Login : <b>${login}</b>, password: <b>${password}</b></p>
            <p>You can change it in your account.</p>
            <p>Enjoy the shopping and stay with us.</p>
        </td>
    </tr>
    <tr>
        <td bgcolor="#FDFCFC" style="padding: 40px 30px 40px 30px;">
            <p>Please do not answer to this e-mail.</p>
            <p>T-Store</p>

        </td>
    </tr>
</table>

</body>
</html>
