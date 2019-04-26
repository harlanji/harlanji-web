{:title "Auth* for static websites"
 :layout :post}
 
Auth is short for authentication and authorization both;

Here we focus mainly on the authentication aspect with an opaque authorization.

Authorization is granted with knowledge of the path component.

Path components can be hashes of a combination of the user, role, and/or buckets.

Here is a quick design that I’ve been thinking about for a little bit.

I have blocks of demos made for all the important functionality here.

In Clojure.

There are a few variations.

![Signed Tokens](/img/2019-04-26-auth-for-static-websites/auth-for-static-sites-signed.png)

The first is a signed JWT scheme that trades a login for a token.

The token can not be decoded to the end user.


![Encrypted Tokens](/img/2019-04-26-auth-for-static-websites/auth-for-static-sites-encrypted.png)

The second is an encrypted JWT scheme where each user has a private key.

The key can be generated within the user-agent, and the private key used to register.

The public key becomes the identity of the user.

This is how a couple of auth schemes that I’ve encountered work.

![Web Procedure](/img/2019-04-26-auth-for-static-websites/auth-for-static-sites-web.png)

Next we think about it in terms of procedural steps.

This is the latter encryption approach since we have a shared secret.

One can visualize an HTTP login process if they’re in my intended audience.


![Web Procedure](/img/2019-04-26-auth-for-static-websites/auth-for-static-sites-email.png)

Finally we have a variation that sends an email to the registered address.

The email contains the token in a query string param, but skips the redirect step.


## Theory

Using hashed path segments is security through obscurity, plain and simple.

The analog hole will always exist, meaning once a document is requested

Expiration time can not be enforced and all control is lost. This is a reality.

Technical measures can be put into place to avoid scripted attacks or scrapes,

Such as destroying the document after a certain point and invalidating the CDN.

But again, this isn’t protection from copies being made no matter the access policy.

The best we can do is create a solution that works well with the flow we desire,

And the one that seems to have the most bang-for-buck is encrypted JWT with Email login.

## Conclusion

There are different permutations of these techniques that could be used.

D4 is close to the solution I have in mind with Magic Link login.

It does not require a CDN with Worker scripts but does still require backend;

It does create a way to share private data and use the token in links or similar.

The CDN could be geared to issue tokens with a KV store or similar.

The most offline alternative would be to generate the site with credentials known ahead of time

And share the tokens thought an out-of-band process similar to the Email flow.

SSL does protect the tokens in any case, so this is not a make-or-break feature;

One can perhaps think of cases where tokens may be at rest and contain sensitive info.