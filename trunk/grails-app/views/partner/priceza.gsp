<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Mr.Kumka Redirect</title>
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
                    <h3>Priceza</h3>
                </div>
                <div  class="col-xs-12">
                    <fieldset class="form">
                        <g:form  url="[action:'pricezaPlanList',controller:'partner']" method="POST">
                            <div class="fieldcontain">

                                <label>Brand: </label>
                                <g:field type="text" name="brand" value="TOYOTA" />
                                <br/><br/>

                                <label>Model: </label>
                                <g:field type="text" name="model" value="Hilux revo" />
                                <br/><br/>

                                <label>Year: </label>
                                <g:field type="text" name="year" value="2015" />
                                <br/><br/>

                                <label>CC: </label>
                                <g:field type="text" name="cc" value="2800" />
                                <br/><br/>


                                <!--<label>Sub Model: </label>-->
                                <!--<g:field type="text" name="subModel" value="Auto / 1.5 / J" />-->
                                <!--<br/><br/>-->

                                <!--<label>CC: </label>-->
                                <!--<g:field type="text" name="cc" value="1500" />-->
                                <!--<br/><br/>-->

                                <!--<g:hiddenField name="username" value="priceza" />-->
                                <!--<g:hiddenField name="password" value="XxWam?D<Zre?X9W%" />-->
                                <!--<g:hiddenField name="planType" value="1" />-->
                                <!--<g:hiddenField name="isMale" value="true" />-->
                                <!--<g:hiddenField name="isSingle" value="false" />-->
                                <!--<g:hiddenField name="yrOfDriving" value="6" />-->
                                <!--<g:hiddenField name="ncbRate" value="N" />-->
                                <!--<g:hiddenField name="isDashcam" value="true" />-->
                                <!--<g:hiddenField name="isGoToWorkOnly" value="false" />-->
                                <!--<g:hiddenField name="dob" value="01/01/1974" />-->
                                <!--<g:hiddenField name="noOfClaim" value="0" />-->

                                <g:submitButton name="submit" value="Submit" />
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