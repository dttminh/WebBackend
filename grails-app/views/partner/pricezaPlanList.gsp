
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Priceza Plan List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Kanit">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

</head>
<body>
<div class="page-redirect" id="RedirectPage">
    <div class="container redirect-bg">
        <div class="row">

            <div class="col-xs-12 text-center">

                <div class="col-xs-12">
                    <h3>Priceza Plan List</h3>
                </div>
                <div class="col-xs-12">
                    <h5>Brand: <b>${result.brand}</b>, Model: <b>${result.model}</b>, Year: <b>${result.year}</b></h5>
                </div>
                <table border="1" width="100%" cellpadding="5" cellspacing="5">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Plan name</th>
                        <th>Model</th>
                        <th>Price Per Year</th>
                        <th>Vehicle Sum Insured Amount</th>
                        <th>Excess Amount</th>
                        <th>Plan Type</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each status="i" in="${result.planList}" var="plan">
                        <tr >
                            <td><a href='pricezaPostData?brand=${result.brand}&model=${plan.model}&year=${result.year}&cc=${result.cc}&pricePerYear=${plan.pricePerYear}&vehiclesumInsuredAmount=${plan.carCoverage.vehiclesumInsuredAmount}&excess=${plan.excessAmount}&planType=${plan.planType}&isPanelWorkshop=${plan.isPanelWorkshop}'>select</a></td>
                            <td>${plan.planNameTH}</td>
                            <td>${plan.model}</td>
                            <td>${plan.pricePerYear}</td>
                            <td>${plan.carCoverage.vehiclesumInsuredAmount}</td>
                            <td>${plan.excessAmount}</td>
                            <td>${plan.planType}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
</body>
</html>