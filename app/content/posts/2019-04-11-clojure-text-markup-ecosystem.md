{:title "Clojure Text Markup Ecosystem"
 :layout :post
 :thumbnail-url "img/2019-04-11-clojure-text-markup-ecosystem/static-dynamic-templates.png"}

Here's a drawing that I made to explain my concept of the gradient between static websites
and SPAs in the Clojure and ClojureScript ecosystem, in my experience.

[![Clojure Text Markup Ecosystem Drawing](/img/2019-04-11-clojure-text-markup-ecosystem/static-dynamic-templates-sm.png)](/img/2019-04-11-clojure-text-markup-ecosystem/static-dynamic-templates.pdf)

There are surely other versions of this that would be valid and could be explored. Enlive
is the newest tool to my landscape and I've yet to Proof-of-concept with it and Kioo, but they
address a problem that I notice with every template system: the first template usually needs to split
up body and head and address the declarative/DOM update imedence mismatch related to title and styles.

I come at this from the infrastructure side, catching myself up on front-end in 2019 after a few years.

Click the image for a PDF.