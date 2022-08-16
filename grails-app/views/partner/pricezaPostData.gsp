<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Priceza Test</title>
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
                    <h3>Priceza Test</h3>
                </div>

                <div  class="col-xs-12">
                    <fieldset class="form">
                        <g:form  url="https://insure.uat-roojai.com/backEnd/partner/callMeBack" method="POST">
                            <div class="fieldcontain">

                                <label>Brand: </label>
                                <g:field type="text" name="brand" value="${params.brand}" readonly="readonly"/>
                                <br/><br/>

                                <label>Model: </label>
                                <g:field type="text" name="model" value="${params.model}" readonly="readonly"/>
                                <br/><br/>

                                <label>Year: </label>
                                <g:field type="text" name="year" value="${params.year}" readonly="readonly"/>
                                <br/><br/>

                                <label>CC: </label>
                                <g:field type="text" name="cc" value="${params.cc}" readonly="readonly"/>
                                <br/><br/>


                                <label>excessAmount: </label>
                                <g:field type="text" name="excessAmount" value="${params.excess}" readonly="readonly"/>
                                <br/><br/>

                                <!--<label>PanelWorkshop </label>-->
                                <!--<g:radio name="isPanelWorkshop" value="true" checked="true"/> true-->
                                <!--<g:radio name="isPanelWorkshop" value="false"/> false-->
                                <!--<br/><br/>-->

                                <!--<label>CompulsoryAvailable </label>-->
                                <!--<g:radio name="isCompulsoryAvailable" value="true"/> true-->
                                <!--<g:radio name="isCompulsoryAvailable" value="false" checked="true"/> false-->
                                <!--<br/><br/>-->

                                <label>planType: </label>
                                <g:field type="text" name="planType" value="${params.planType}" readonly="readonly"/>
                                <br/><br/>


                                <label>vehiclesumInsuredAmount: </label>
                                <g:field type="text" name="vehiclesumInsuredAmount" value="${params.vehiclesumInsuredAmount}" readonly="readonly" />
                                <br/><br/>


                                <label>pricePerYear: </label>
                                <g:field type="text" name="pricePerYear" value="${params.pricePerYear}" readonly="readonly"/>
                                <br/><br/>


                                <label>customerEmail: </label>
                                <g:field type="text" name="customerEmail" value="" />
                                <br/><br/>


                                <label>customerPhoneNumber: </label>
                                <g:field type="text" name="customerPhoneNumber" value="0819999990" />
                                <br/><br/>


                                <label>customerLineID: </label>
                                <g:field type="text" name="customerLineID" value="" />
                                <br/><br/>

                                <g:hiddenField name="prefix" value="Mr" />

                                <label>name: </label>
                                <g:field type="text" name="name" value="Dev Roojai" />
                                <br/><br/>


                                <g:hiddenField name="isMale" value="true" />

                                <g:hiddenField name="isSingle" value="false" />

                                <g:hiddenField name="yrOfDriving" value="6" />

                                <g:hiddenField name="noOfClaim" value="0" />


                                <g:hiddenField name="lang" value="en" />
                                <g:hiddenField name="planID" value="32f4a9sewrf3fw9" />
                                <g:hiddenField name="nonce" value="priceza" />
                                <g:hiddenField name="dob" value="01/01/1984" />
                                <g:hiddenField name="isGoToWorkOnly" value="true" />
                                <g:hiddenField name="isDashcam" value="true" />
                                <g:hiddenField name="ncbRate" value="N" />
                                <g:hiddenField name="isPanelWorkshop" value="${params.isPanelWorkshop}" />
                                <g:hiddenField name="isCompulsoryAvailable" value="false" />
                                <g:hiddenField name="username" value="priceza" />
                                <g:hiddenField name="password" value="test" />

                                <g:submitButton name="go" value="Submit" />
                            </div>
                        </g:form>
                    </fieldset>
                </div>

            </div>

        </div>
    </div>
</div>
</body>
</html>