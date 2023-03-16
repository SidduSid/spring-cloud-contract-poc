package contracts
import org.springframework.cloud.contract.spec.Contract
Contract.make {
    description"should return student details for existing student"
    request {
        url("/producer/student-details/1")
        method GET()
    }
    response {
        status 200
        headers {
            contentType applicationJson()
        }
        body(id: 1, name: "ABI", age: 27, status: "FOUND")
    }
}