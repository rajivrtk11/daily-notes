/**
1. LLD round preparation strategy
 -> Prepare basc design patterns and oops
    // Creational design pattern
    -> Singleton, builder, prototype design pattern, factory, abstract factory
    // Structural design pattern
    -> Adapter dp, Facade design pattern, Decorator dp, Flyweight design pattern
    // Behavioral design pattern
    -> Strategy dp, Observer design pattern
 
 -> Problems on low level design
    -> Tic tac toe
    -> Parking lot
    -> Split wise
    -> Snake and ladder
    -> Meeting scheduler
    -> Logger
    -> Design file system
 
 -> High level design
    -> Revise db
    -> Caching
    -> Load balancer
*/

/*
// Design parking lot
What all the entities are going to be there in the parking lot
-> Customer  -> Entry gate -> Makes entry -> Assign parking slot -> Issue ticket -> Store the ticket in the hashmap/or db
-> While exit do we need to make the entry in the db of the vehicle 

2. ParkingLot
    -> id
    -> name
    -> floorToSlotMap
    -> noOfFloor
    -> slotsOnEachFloor

4. ParkingFloor
    -> floorId
    -> floorName
    -> noOfSlots
    -> List<ParkingSlot> parkingSlot

3. ParkingSlot
    -> slotId
    -> slotType
    -> isAvailable
    -> parkedVehicle

1. Vehicle
    -> id
    -> registrationNo
    -> color
    -> entryTime
    -> exitTime

5. Ticket
    -> ticketId
    -> vehicle

Enum 
VehicleType
Command => Display, Park, Unpark, ShowVaccant, ShowFilled
 
Services
-> ParkingLotService
    -> display
    -> parkVehicle
    -> unparkVehicle
    -> showVaccant
    -> showFilled

Repository
    -> ParkingLotRepository
       - floorToSlotMap
       intializeDb(floor)

*/