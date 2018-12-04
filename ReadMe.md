This is Token based application using JWT and It does not need to store on database Since it is calculated on the fly using JWT.io

Run API: http://localhost:9092/api/token/user/login
IT will return : __Secure-Fgp
 as cookie
 Then append that cookie in postman header ("Cookie") :
 with __Secure-Fgp=16FA80F07A27F244EC9034F781760C45356BE09AB2DAC4A1EADC3A6D6EC1B7DDC

 And add another header with ("authorization") : response of first api append "Bearer "

 Token expires on 15 min after creation

We match the hex match with we have on claim with the cookie we have shared first