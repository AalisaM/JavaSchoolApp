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
        <td bgcolor="#FDFCFC" style="padding: 40px 30px 20px 30px;">
            <p>Dear ${fullName},</p>
            <p>You have made next order in <b>T-Store</b>: </p>
            <table>
                <colgroup>
                    <col span="1" style="width: 50%;">
                    <col span="1" style="width: 18%;">
                    <col span="1" style="width: 14%;">
                    <col span="1" style="width: 18%;">

                </colgroup>
                <thead>
                    <th style="col">Product</th>
                    <th>Price</th>
                    <th>Amount</th>
                    <th>Total</th>
                </thead>
                <tbody>
                    <#assign cost = 0>
                    <#list orderProduct as op>
                        <tr>
                            <td>${op.getProductName()}</td>
                            <td style="text-align:center">${op.getPrice() / op.getAmount()}</td>
                            <td style="text-align:center">${op.getAmount()}</td>
                            <td style="text-align:center">${op.getPrice()}</td>
                        </tr>
                        <#assign cost += op.getPrice()>

                    </#list>
                </tbody>
            </table>

            <p>Total price is: ${cost}</p>
            <p> You can track your order in <a href='http://192.168.99.100:8086/store/account/'>account</a>.
                and in <a href='http://192.168.99.100:8086/store/order/${id}'>order page</a>.
                Good luck and enjoy shopping!</p>

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
