# Low-Level Design (LLD) Patterns

## 1. Creational Design Patterns
### **1.1 Factory Pattern**
- Provides an interface for creating objects without exposing the instantiation logic.
- **Example Use Case**: `LoggerFactory` to create different types of loggers.

### **1.2 Singleton Pattern**
- Ensures only one instance of a class exists in the system.
- **Example Use Case**: Database connection pool, Configuration manager.

### **1.3 Builder Pattern**
- Used to construct complex objects step by step.
- **Example Use Case**: Creating a complex `User` object with optional fields.

### **1.4 Prototype Pattern**
- Cloning objects instead of creating new ones to improve performance.
- **Example Use Case**: Cloning objects when object creation is expensive.

---

## 2. Structural Design Patterns
### **2.1 Adapter Pattern**
- Allows incompatible interfaces to work together.
- **Example Use Case**: Converting `XML` data to `JSON` for compatibility.

### **2.2 Decorator Pattern**
- Adds behavior to objects dynamically without modifying the original class.
- **Example Use Case**: Adding encryption or compression to file I/O operations.

### **2.3 Facade Pattern**
- Provides a simplified interface to a complex subsystem.
- **Example Use Case**: `Hibernate` provides a simple API while handling complex ORM logic internally.

### **2.4 Proxy Pattern**
- Controls access to an object.
- **Example Use Case**: Virtual proxy for lazy initialization, security proxy for access control.

---

## 3. Behavioral Design Patterns
### **3.1 Observer Pattern**
- Defines a dependency where multiple objects listen to one object’s state changes.
- **Example Use Case**: Event listeners in GUIs, stock market price updates.

### **3.2 Strategy Pattern**
- Defines a family of algorithms and selects one dynamically.
- **Example Use Case**: Sorting algorithms (`MergeSort`, `QuickSort`), payment methods (`CreditCard`, `UPI`).

### **3.3 Command Pattern**
- Encapsulates requests as objects for flexible execution.
- **Example Use Case**: Undo/Redo functionality in text editors.

### **3.4 Chain of Responsibility Pattern**
- Passes requests along a chain of handlers until one processes it.
- **Example Use Case**: Logging framework, middleware in web servers.

---

## Most Widely Used Patterns in Real-World Applications
1. **Singleton** - Used in configuration, logging, caching.
2. **Factory** - Used in frameworks for object creation.
3. **Observer** - Used in event-driven systems.
4. **Strategy** - Used for flexible algorithm selection.
5. **Facade** - Used in frameworks and APIs.
6. **Decorator** - Used in UI components, I/O streams.

---


