# Bootcamp MLB Wave1 Documentation (Group7)

---

## Application base info

- Base path: **/api/v1/fresh-products/**
- Further documentation about the applications endpoints can be found at **/swagger-ui.html**
- Authentication is needed to access this application requirements, authenticate via **/login**

---

## Enviroment setup



---

### UserStory 001
**US-code:** ml-insert-batch-in-fulfillment-warehouse-01

##### DTO Changes:

**Endpoint**: POST /inboundorder

We chose to not receive the number(id) for the **InboundOrder** and its **Batch(s)** at creation, leaving it to Hibernate to manage these entities IDs.

Valid Request:

```json
{
    "inboundOrder":{
        "orderDate": "2021-07-01", 
        "section": {
        "sectionCode": "CAJF001",
        "warehouseCode": "CAJF"
        }, 
        "batchStock":[
            {
            "productId": "51b3b287-0b78-484c-90c3-606c4bae9401",
            "currentTemperature": 10.0,
            "minimumTemperature": 5.0,
            "initialQuantity": 500,
            "currentQuantity": 500,
            "manufacturingDate": "2021-06-10",
            "manufacturingTime": "2021-06-10 18:30:49",
            "dueDate": "2021-12-15"
            },
            {
            "productId": "fa0d9b2e-3eac-417e-8ee6-f26037336522",
            "currentTemperature": 12.0,
            "minimumTemperature": 5.0,
            "initialQuantity": 500,
            "currentQuantity": 500,
            "manufacturingDate": "2021-06-10",
            "manufacturingTime": "2021-06-10 18:30:49",
            "dueDate": "2021-12-15"
            }
        ]
    }
}
```

#### Sequence diagram of the creation of a InboundOrder
[Link](https://mermaid-js.github.io/mermaid-live-editor/view/#eyJjb2RlIjoic2VxdWVuY2VEaWFncmFtXG4gICAgSW5ib3VuZE9yZGVyQ29udHJvbGxlci0-PitJbmJvdW5kT3JkZXJTZXJ2aWNlOiBjcmVhdGVJbmJvdW5kT3JkZXIoKVxuICAgIEluYm91bmRPcmRlclNlcnZpY2UtPj4rU3VwZXJ2aXNvclJlcG9zaXRvcnk6IGZpbmRCeUlkKClcbiAgICBTdXBlcnZpc29yUmVwb3NpdG9yeS0-Pi1JbmJvdW5kT3JkZXJTZXJ2aWNlOiBsb2dnZWQgc3VwZXJ2aXNvclxuICAgIEluYm91bmRPcmRlclNlcnZpY2UtPj4rU2VjdGlvblJlcG9zaXRvcnk6IGZpbmRCeUlkKClcbiAgICBTZWN0aW9uUmVwb3NpdG9yeS0-Pi1JbmJvdW5kT3JkZXJTZXJ2aWNlOiBzZWN0aW9uXG4gICAgSW5ib3VuZE9yZGVyU2VydmljZS0-PitQcm9kdWN0UmVwb3NpdG9yeTogZmluZEFsbEJ5SWQoKVxuICAgIFByb2R1Y3RSZXBvc2l0b3J5LT4-LUluYm91bmRPcmRlclNlcnZpY2U6IHByb2R1Y3RzXG4gICAgSW5ib3VuZE9yZGVyU2VydmljZS0-PiBJbmJvdW5kT3JkZXJTZXJ2aWNlOiBWYWxpZGF0aW9uc1xuICAgIEluYm91bmRPcmRlclNlcnZpY2UtPj4rSW5ib3VuZE9yZGVyUmVwb3NpdG9yeTogc2F2ZSgpXG4gICAgSW5ib3VuZE9yZGVyUmVwb3NpdG9yeS0-PitCYXRjaFJlcG9zaXRvcnk6IHNhdmVBbGwoKVxuICAgIEJhdGNoUmVwb3NpdG9yeS0-Pi1JbmJvdW5kT3JkZXJSZXBvc2l0b3J5OiBjcmVhdGVkXG4gICAgSW5ib3VuZE9yZGVyUmVwb3NpdG9yeS0-Pi1JbmJvdW5kT3JkZXJTZXJ2aWNlOiBjcmVhdGVkXG5cbiAgICBJbmJvdW5kT3JkZXJTZXJ2aWNlLT4-LUluYm91bmRPcmRlckNvbnRyb2xsZXI6IENyZWF0ZWQgMjAxXG4iLCJtZXJtYWlkIjoie1xuICBcInRoZW1lXCI6IFwiZGVmYXVsdFwiXG59IiwidXBkYXRlRWRpdG9yIjp0cnVlLCJhdXRvU3luYyI6dHJ1ZSwidXBkYXRlRGlhZ3JhbSI6dHJ1ZX0)

#### Sequencce diagram of the update of a InboundOrder
[Link](https://mermaid-js.github.io/mermaid-live-editor/view/#eyJjb2RlIjoic2VxdWVuY2VEaWFncmFtXG4gICAgSW5ib3VuZE9yZGVyQ29udHJvbGxlci0-PitJbmJvdW5kT3JkZXJTZXJ2aWNlOiB1cGRhdGVJbmJvdW5kT3JkZXIoKVxuICAgIEluYm91bmRPcmRlclNlcnZpY2UtPj4rSW5ib3VuZE9yZGVyUmVwb3NpdG9yeTogZmluZEJ5SWQoKVxuICAgIEluYm91bmRPcmRlclJlcG9zaXRvcnktPj4tSW5ib3VuZE9yZGVyU2VydmljZTogSW5ib3VuZE9yZGVyXG4gICAgSW5ib3VuZE9yZGVyU2VydmljZS0-PiBJbmJvdW5kT3JkZXJTZXJ2aWNlOiBWYWxpZGF0ZSBpZiBvcmRlciBleHNpdHNcbiAgICBJbmJvdW5kT3JkZXJTZXJ2aWNlLT4-K0JhdGNoUmVwb3NpdG9yeTogZmluZEFsbEJ5SWQoKVxuICAgIEJhdGNoUmVwb3NpdG9yeS0-Pi1JbmJvdW5kT3JkZXJSZXBvc2l0b3J5OiBiYXRjaGVzXG4gICAgSW5ib3VuZE9yZGVyUmVwb3NpdG9yeS0-PitJbmJvdW5kT3JkZXJTZXJ2aWNlOiByZXF1ZXN0ZWQgYmF0Y2hlc1xuICAgIEluYm91bmRPcmRlclNlcnZpY2UtPj4gSW5ib3VuZE9yZGVyU2VydmljZTogVmFsaWRhdGUgaWYgdGhlIHJlcXVlc3RlZCBiYXRjaChlcykgYXJlIGZyb20gdGhlIEluYm91bmRPcmRlclxuICAgIEluYm91bmRPcmRlclNlcnZpY2UtPj4rU3VwZXJ2aXNvclJlcG9zaXRvcnk6IGZpbmRCeUlkKClcbiAgICBTdXBlcnZpc29yUmVwb3NpdG9yeS0-Pi1JbmJvdW5kT3JkZXJTZXJ2aWNlOiBsb2dnZWQgc3VwZXJ2aXNvclxuICAgIEluYm91bmRPcmRlclNlcnZpY2UtPj4rU2VjdGlvblJlcG9zaXRvcnk6IGZpbmRCeUlkKClcbiAgICBTZWN0aW9uUmVwb3NpdG9yeS0-Pi1JbmJvdW5kT3JkZXJTZXJ2aWNlOiBzZWN0aW9uXG4gICAgSW5ib3VuZE9yZGVyU2VydmljZS0-PitQcm9kdWN0UmVwb3NpdG9yeTogZmluZEFsbEJ5SWQoKVxuICAgIFByb2R1Y3RSZXBvc2l0b3J5LT4-LUluYm91bmRPcmRlclNlcnZpY2U6IHByb2R1Y3RzXG4gICAgSW5ib3VuZE9yZGVyU2VydmljZS0-PiBJbmJvdW5kT3JkZXJTZXJ2aWNlOiBWYWxpZGF0aW9uc1xuICAgIEluYm91bmRPcmRlclNlcnZpY2UtPj4rSW5ib3VuZE9yZGVyUmVwb3NpdG9yeTogdXBkYXRlKClcbiAgICBJbmJvdW5kT3JkZXJSZXBvc2l0b3J5LT4-K0JhdGNoUmVwb3NpdG9yeTogdXBkYXRlQWxsKClcbiAgICBCYXRjaFJlcG9zaXRvcnktPj4tSW5ib3VuZE9yZGVyUmVwb3NpdG9yeTogdXBkYXRlZFxuICAgIEluYm91bmRPcmRlclJlcG9zaXRvcnktPj4tSW5ib3VuZE9yZGVyU2VydmljZTogdXBkYXRlZFxuXG4gICAgSW5ib3VuZE9yZGVyU2VydmljZS0-Pi1JbmJvdW5kT3JkZXJDb250cm9sbGVyOiBDcmVhdGVkIDIwMVxuIiwibWVybWFpZCI6IntcbiAgXCJ0aGVtZVwiOiBcImRlZmF1bHRcIlxufSIsInVwZGF0ZUVkaXRvciI6dHJ1ZSwiYXV0b1N5bmMiOnRydWUsInVwZGF0ZURpYWdyYW0iOnRydWV9)

---

### UserStory 002
**US-code:** ml-add-products-to-cart-01

---

### UserStory 003
**US-code:** ml-check-product-location-in-warehouse-01

**Endpoint**: POST /list?productId=<>&sortParam=<>

- The warehouse in which we search for the batch list is the same warehouse that the supervisor who authenticated the call is registered at.
- If no sortParam is given it will default to order the response by the batch currentQuantity.
- The sortParam variable will only accept C or F as values. (C as for ordering by the currentQuantity, and F for the dueDate)

#### Sequence diagram of the response list of batches by any given product

[Link](https://mermaid-js.github.io/mermaid-live-editor/view/#eyJjb2RlIjoic2VxdWVuY2VEaWFncmFtXG4gICAgSW5ib3VuZE9yZGVyQ29udHJvbGxlci0-PitJbmJvdW5kT3JkZXJTZXJ2aWNlOiBsaXN0UHJvZHVjdEJhdGNoU3RvY2soKVxuICAgIFxuICAgIEluYm91bmRPcmRlclNlcnZpY2UtPj5JbmJvdW5kT3JkZXJTZXJ2aWNlOiBWYWxpZGF0ZSBzb3J0IHBhcmFtZXRlclxuXG4gICAgSW5ib3VuZE9yZGVyU2VydmljZS0-PitQcm9kdWN0UmVwb3NpdG9yeTogZmluZEJ5SWQoKVxuICAgIFByb2R1Y3RSZXBvc2l0b3J5LT4-LUluYm91bmRPcmRlclNlcnZpY2U6IHByb2R1Y3RcblxuICAgIEluYm91bmRPcmRlclNlcnZpY2UtPj4rU3VwZXJ2aXNvclJlcG9zaXRvcnk6IGZpbmRCeUlkKClcbiAgICBTdXBlcnZpc29yUmVwb3NpdG9yeS0-Pi1JbmJvdW5kT3JkZXJTZXJ2aWNlOiBsb2dnZWQgc3VwZXJ2aXNvclxuICAgIFxuICAgIEluYm91bmRPcmRlclNlcnZpY2UtPj4rU2VjdGlvblJlcG9zaXRvcnk6IGZpbmRCeVdhcmVob3VzZUNvZGVBbmRDYXRlZ29yeSgpXG4gICAgU2VjdGlvblJlcG9zaXRvcnktPj4tSW5ib3VuZE9yZGVyU2VydmljZTogc2VjdGlvblxuXG4gICAgSW5ib3VuZE9yZGVyU2VydmljZS0-PitCYXRjaFJlcG9zaXRvcnk6IGZpbmRCYXRjaGVzQnlQcm9kdWN0QW5kV2FyZWhvdXNlKClcbiAgICBCYXRjaFJlcG9zaXRvcnktPj4tSW5ib3VuZE9yZGVyU2VydmljZTogYmF0Y2hlc1xuXG4gICAgSW5ib3VuZE9yZGVyU2VydmljZS0-PkluYm91bmRPcmRlclNlcnZpY2U6IGZpbHRlciBhbmQgbWFwIGJhdGNoZXNcblxuICAgIEluYm91bmRPcmRlclNlcnZpY2UtPj4tSW5ib3VuZE9yZGVyQ29udHJvbGxlcjogT0sgMjAwXG4iLCJtZXJtYWlkIjoie1xuICBcInRoZW1lXCI6IFwiZGVmYXVsdFwiXG59IiwidXBkYXRlRWRpdG9yIjp0cnVlLCJhdXRvU3luYyI6dHJ1ZSwidXBkYXRlRGlhZ3JhbSI6dHJ1ZX0)

---

### UserStory 004
**US-code:** ml-check-product-stock-in-warehouses-04 \
**Endpoint**: GET /warehouse/productId = id product \
We decide to change the variable name querytype to productid on the endpoint

[Link](https://mermaid-js.github.io/mermaid-live-editor/view/#eyJjb2RlIjoic2VxdWVuY2VEaWFncmFtXG5XYXJlaG91c2VDb250cm9sbGVyLT4-K1dhcmVob3VzZVNlcnZpY2U6IGdldFByb2R1Y3RzSW5BbGxXYXJlaG91c2VzKClcbldhcmVob3VzZVNlcnZpY2UtPj4rUHJvZHVjdFJlcG9zaXRvcnk6IGZpbmRCeUlkKClcblByb2R1Y3RSZXBvc2l0b3J5LT4-LVdhcmVob3VzZVNlcnZpY2U6IHByb2R1Y3RcbldhcmVob3VzZVNlcnZpY2UtPj4rV2FyZWhvdXNlUmVwb3NpdG9yeTogZmluZEFsbCgpXG5XYXJlaG91c2VSZXBvc2l0b3J5LT4-LVdhcmVob3VzZVNlcnZpY2U6IHdhcmVob3VzZXNcbldhcmVob3VzZVNlcnZpY2UtPj4rQmF0Y2hSZXBvc2l0b3J5OiBmaW5kQmF0Y2hlc0J5UHJvZHVjdEFuZFdhcmVob3VzZSgpXG5CYXRjaFJlcG9zaXRvcnktPj4tV2FyZWhvdXNlU2VydmljZTogYmF0Y2hlc1xuV2FyZWhvdXNlU2VydmljZS0-PldhcmVob3VzZVNlcnZpY2U6IHZhbGlkYXRpb25zXG5XYXJlaG91c2VTZXJ2aWNlLT4-LVdhcmVob3VzZUNvbnRyb2xsZXI6IHByb2R1Y3RXYXJlaG91c2VEVE9cbiAgXG4gICAgICAgICAgICAiLCJtZXJtYWlkIjoie1xuICBcInRoZW1lXCI6IFwiZGVmYXVsdFwiXG59IiwidXBkYXRlRWRpdG9yIjp0cnVlLCJhdXRvU3luYyI6dHJ1ZSwidXBkYXRlRGlhZ3JhbSI6dHJ1ZX0)

---

### UserStory 005

#### Sequence diagram of the response list of products with the due date filtered by de current date plus the given day amaunt
[Link](https://mermaid-js.github.io/mermaid-live-editor/view/#eyJjb2RlIjoic2VxdWVuY2VEaWFncmFtXG4gICAgQmF0Y2hDb250cm9sbGVyLT4-K0JhdGNoU2VydmljZTogZ2V0QmF0Y2hlc1dpdGhFeHBpcmluZ1Byb2R1Y3RzKClcbiAgICBCYXRjaFNlcnZpY2UtPj4rU3VwZXJ2aXNvclJlcG9zaXRvcnk6IGZpbmRCeUlkKClcbiAgICBTdXBlcnZpc29yUmVwb3NpdG9yeS0-Pi1CYXRjaFNlcnZpY2U6IFN1cGVydmlzb3JcbiAgICBCYXRjaFNlcnZpY2UtPj4rV2FyZWhvdXNlUmVwb3NpdG9yeTogZmluZEJ5SWQoKVxuICAgIFdhcmVob3VzZVJlcG9zaXRvcnktPj4tQmF0Y2hTZXJ2aWNlOiBXYXJlaG91c2VcbiAgICBCYXRjaFNlcnZpY2UtPj4rQmF0Y2hSZXBvc2l0b3J5OiBmaW5kQWxsQnlPcmRlck51bWJlckZpbHRlcigpXG4gICAgQmF0Y2hSZXBvc2l0b3J5LT4-LUJhdGNoU2VydmljZTogTGlzdDxCYXRjaD5cbiAgICBCYXRjaFNlcnZpY2UtPj4gQmF0Y2hTZXJ2aWNlOiBnZXRPcmRlckxpc3QoKVxuICAgIEJhdGNoU2VydmljZS0-Pi1CYXRjaENvbnRyb2xsZXI6IEV4cGlyaW5nUHJvZHVjdHNEVE9cblxuXG4gICAgXG4iLCJtZXJtYWlkIjoie1xuICBcInRoZW1lXCI6IFwiZGVmYXVsdFwiXG59IiwidXBkYXRlRWRpdG9yIjp0cnVlLCJhdXRvU3luYyI6dHJ1ZSwidXBkYXRlRGlhZ3JhbSI6dHJ1ZX0)

#### Sequence diagram of the response list of products with the due date filtered by de current date plus the given day amaunt and the product category
[Link](https://mermaid-js.github.io/mermaid-live-editor/view/#eyJjb2RlIjoic2VxdWVuY2VEaWFncmFtXG4gICAgQmF0Y2hDb250cm9sbGVyLT4-K0JhdGNoU2VydmljZTogZ2V0QmF0Y2hlc1dpdGhFeHBpcmluZ1Byb2R1Y3RzV2l0aEZpbHRlcnMoKVxuICAgIEJhdGNoU2VydmljZS0-PiBCYXRjaFNlcnZpY2U6IGdldEJhdGNoZXNXaXRoRXhwaXJpbmdQcm9kdWN0cygpXG4gICAgQmF0Y2hTZXJ2aWNlLT4-K1N1cGVydmlzb3JSZXBvc2l0b3J5OiBmaW5kQnlJZCgpXG4gICAgU3VwZXJ2aXNvclJlcG9zaXRvcnktPj4tQmF0Y2hTZXJ2aWNlOiBTdXBlcnZpc29yXG4gICAgQmF0Y2hTZXJ2aWNlLT4-K1dhcmVob3VzZVJlcG9zaXRvcnk6IGZpbmRCeUlkKClcbiAgICBXYXJlaG91c2VSZXBvc2l0b3J5LT4-LUJhdGNoU2VydmljZTogV2FyZWhvdXNlXG4gICAgQmF0Y2hTZXJ2aWNlLT4-K0JhdGNoUmVwb3NpdG9yeTogZmluZEFsbEJ5T3JkZXJOdW1iZXJGaWx0ZXIoKVxuICAgIEJhdGNoUmVwb3NpdG9yeS0-Pi1CYXRjaFNlcnZpY2U6IExpc3Q8QmF0Y2g-XG4gICAgQmF0Y2hTZXJ2aWNlLT4-IEJhdGNoU2VydmljZTogZ2V0T3JkZXJMaXN0KClcbiAgICBCYXRjaFNlcnZpY2UtPj4tQmF0Y2hDb250cm9sbGVyOiBFeHBpcmluZ1Byb2R1Y3RzRFRPXG5cbiAgICAgICAgICAgICIsIm1lcm1haWQiOiJ7XG4gIFwidGhlbWVcIjogXCJkZWZhdWx0XCJcbn0iLCJ1cGRhdGVFZGl0b3IiOnRydWUsImF1dG9TeW5jIjp0cnVlLCJ1cGRhdGVEaWFncmFtIjp0cnVlfQ)
