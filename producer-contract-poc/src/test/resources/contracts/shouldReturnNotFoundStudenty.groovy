package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return NotFound for Student Not existing student"

    request {
        url("/producer/student-details/100")
        method GET()
    }
    response {

        status 200
        headers {
            contentType applicationJson()
        }
        body(
                id: 0,
                name: "",
                age: 0,
                status: "NOT_FOUND"
        )

    }
}