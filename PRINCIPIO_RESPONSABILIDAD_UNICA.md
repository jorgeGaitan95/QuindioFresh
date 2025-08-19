# 🎯 Principio de Responsabilidad Única (SRP) - SOLID

## 📚 **¿Qué es el Principio de Responsabilidad Única?**

**"Una clase debe tener una sola razón para cambiar"** - Robert C. Martin

En otras palabras, una clase debe tener **una sola responsabilidad** o función específica.

## 🔍 **Problema: Clase con Múltiples Responsabilidades**

### **Antes de la Refactorización:**
La clase `Pedido` hacía **TODO**:
- ✅ Gestionar información del pedido
- ❌ Validar tarjetas de crédito
- ❌ Procesar pagos
- ❌ Calcular costos
- ❌ Enviar notificaciones SMS

**Problemas:**
- Clase muy grande y difícil de mantener
- Si cambia la lógica de pagos, hay que modificar la clase Pedido
- Si cambia la lógica de SMS, hay que modificar la clase Pedido
- Difícil de probar cada funcionalidad por separado

## 🛠️ **Solución: Objetos del Mundo Real**

### **Después de la Refactorización:**

#### **1. TarjetaCredito.java** - Objeto de Tarjeta de Crédito
```java
// Representa una tarjeta de crédito del mundo real
public class TarjetaCredito {
    private String numero;
    private String titular;
    private String fechaVencimiento;
    private String cvv;
    
    public boolean esValida() { ... }
    public String getNumeroEnmascarado() { ... }
}
```

#### **2. Pago.java** - Objeto de Pago
```java
// Representa un pago del mundo real
public class Pago {
    private TarjetaCredito tarjeta;  // "Tiene una" tarjeta
    private float monto;
    private boolean procesado;
    
    public boolean procesar() { ... }
    public String obtenerResumen() { ... }
}
```

#### **3. Envio.java** - Objeto de Envío
```java
// Representa un envío del mundo real
public class Envio {
    private MetodoEnvio metodoEnvio;
    private float costo;
    private String direccionDestino;
    private String numeroSeguimiento;
    
    public void procesarEnvio() { ... }
    public String obtenerResumen() { ... }
}
```

#### **4. NotificacionSMS.java** - Objeto de Notificación
```java
// Representa una notificación SMS del mundo real
public class NotificacionSMS {
    private String numeroCelular;
    private String mensaje;
    
    public NotificacionSMS(String numeroCelular, Pedido pedido) { ... }
    public boolean enviar() { ... }
}
```

#### **5. Pedido.java (Refactorizada)**
```java
// Ahora usa composición de objetos del mundo real
public class Pedido {
    private Envio envio;           // "Tiene un" envío
    private Pago pago;             // "Tiene un" pago
    private NotificacionSMS notificacion; // "Tiene una" notificación
    
    // Solo coordina, no implementa la lógica específica
}
```

## ✅ **Beneficios de la Refactorización**

### **1. Modelado del Mundo Real**
- **TarjetaCredito**: Representa una tarjeta real con validación y enmascaramiento
- **Pago**: Representa un pago real que usa una tarjeta
- **Envío**: Representa un envío real con seguimiento y costos
- **NotificaciónSMS**: Representa una notificación real con envío

### **2. Separación de Responsabilidades**
- **TarjetaCredito**: Solo valida y enmascara datos de tarjeta
- **Pago**: Solo procesa el pago usando una tarjeta
- **Envío**: Solo maneja el envío y seguimiento
- **NotificaciónSMS**: Solo envía notificaciones

### **3. Mantenibilidad**
- Si cambia la validación de tarjetas → Solo modificar `TarjetaCredito`
- Si cambia la lógica de pagos → Solo modificar `Pago`
- Si cambia la lógica de envíos → Solo modificar `Envio`
- Si cambia la lógica de SMS → Solo modificar `NotificacionSMS`

### **4. Testabilidad**
- Cada objeto se puede probar de forma independiente
- Más fácil escribir pruebas unitarias específicas

### **5. Reutilización**
- `TarjetaCredito` se puede usar en otros contextos (pagos, validaciones)
- `Pago` se puede usar en otros contextos (facturas, suscripciones)
- `Envio` se puede usar para otros tipos de envíos
- `NotificacionSMS` se puede usar para otros tipos de notificaciones

### **6. Legibilidad**
- Cada objeto tiene un propósito claro del mundo real
- Código más fácil de entender y navegar

## 🎓 **Conceptos Clave para Programación II**

### **¿Por qué Composición en lugar de Agregación o Asociación?**

Una pregunta valida es: *"¿Por qué no usar agregación o asociación simple?"*

#### **1. Tipos de Relaciones en UML**

```java
// 🔴 ASOCIACIÓN SIMPLE: "Usa" (débil)
public class Pedido {
    public void procesarConTarjeta(TarjetaCredito tarjeta) { ... }
    // La tarjeta existe independientemente del pedido
}

// 🟡 AGREGACIÓN: "Contiene" (moderada) 
public class Pedido {
    private List<Producto> productos; // Los productos pueden existir sin el pedido
    // Si se elimina el pedido, los productos siguen existiendo
}

// 🟢 COMPOSICIÓN: "Tiene" (fuerte)
public class Pedido {
    private Envio envio;     // El envío NO existe sin el pedido
    private Pago pago;       // El pago NO existe sin el pedido
}
```

#### **2. ¿Por qué Composición en Nuestro Caso?**

**Ciclo de Vida:**
- **Envío**: Solo existe mientras existe el pedido
- **Pago**: Solo existe mientras existe el pedido  
- **NotificaciónSMS**: Solo existe mientras existe el pedido

**Responsabilidad:**
- El pedido es **responsable** de crear y destruir estos objetos
- Si se elimina el pedido, estos objetos también se eliminan

#### **3. Comparación Práctica**

```java
// ❌ ASOCIACIÓN: Los objetos existen independientemente
public class Pedido {
    public void procesarPago(TarjetaCredito tarjeta) { ... }
    public void enviarNotificacion(NotificacionSMS sms) { ... }
}
// Problema: ¿Quién crea estos objetos? ¿Quién los gestiona?

// ✅ COMPOSICIÓN: El pedido gestiona todo
public class Pedido {
    private Pago pago;             // El pedido crea y gestiona el pago
    private NotificacionSMS notificacion; // El pedido crea y gestiona la notificación
}
// Ventaja: Responsabilidad clara, ciclo de vida controlado
```

#### **4. Cuándo Usar Cada Una**

- **Asociación**: Cuando los objetos son independientes (Cliente ↔ Pedido)
- **Agregación**: Cuando los objetos pueden existir por separado (Pedido ↔ Producto)
- **Composición**: Cuando los objetos son parte integral (Pedido ↔ Envío, Pago)

### **Modelado de Objetos**
```java
// Cada objeto representa una entidad del mundo real
TarjetaCredito tarjeta = new TarjetaCredito("1234567890123456", "Juan Pérez", "12/25", "123");
Pago pago = new Pago(tarjeta, 50000);
Envio envio = new Envio(MetodoEnvio.EXPRESS, "Calle 15 #23-45");
NotificacionSMS sms = new NotificacionSMS("3001234567", pedido);
```

### **Encapsulación**
```java
// Cada objeto encapsula su lógica específica
public class TarjetaCredito {
    private String numero; // Datos privados
    public boolean esValida() { ... } // Comportamiento público
}

public class Pago {
    private TarjetaCredito tarjeta; // Datos privados
    public boolean procesar() { ... } // Comportamiento público
}
```

## 🚀 **¿Cómo Aplicar SRP con Objetos del Mundo Real?**

### **1. Identifica las Entidades**
- ¿Qué objetos del mundo real están involucrados?
- ¿Qué información y comportamiento tiene cada uno?

### **2. Crea Clases para Cada Entidad**
- Una clase por cada objeto del mundo real
- Incluye atributos y métodos relevantes

### **3. Usa Composición**
- Una clase principal puede "tener" otros objetos
- Delega responsabilidades a los objetos especializados

### **4. Mantén la Cohesión**
- Cada objeto debe tener métodos relacionados entre sí
- Evita objetos que hagan cosas muy diferentes

## 📝 **Ejercicio Práctico**

**Antes:** Una clase `Estudiante` que maneja:
- Información personal
- Calificaciones
- Envío de emails
- Cálculo de promedios
- Validación de datos

**Después:** Dividir en objetos del mundo real:
- `Estudiante` (información personal)
- `Calificacion` (nota, materia, fecha)
- `Email` (destinatario, asunto, mensaje, estado)
- `Promedio` (calificaciones, cálculo, resultado)

## 🎯 **Resumen**

El principio de Responsabilidad Única con objetos del mundo real nos ayuda a crear código:
- ✅ **Más fácil de mantener** - Cada objeto tiene una responsabilidad clara
- ✅ **Más fácil de probar** - Cada objeto se puede probar independientemente
- ✅ **Más fácil de reutilizar** - Los objetos se pueden usar en otros contextos
- ✅ **Más fácil de entender** - Representa el mundo real de manera natural
