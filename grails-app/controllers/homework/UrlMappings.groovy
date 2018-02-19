package homework

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "Car", action: "index")
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
