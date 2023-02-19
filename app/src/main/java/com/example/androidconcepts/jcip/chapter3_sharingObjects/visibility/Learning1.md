1. Operations done in a thread A might be rearranged and become visible to other threads in the different order or might
   not be visible at all. This can cause heavy complications in a program logic.

2. 64 bit DataTypes like double and long are treated as 2 32 bits. Hence other threads might read
   incorrect value (first 32 bit or second 32 bit).

3. Everything Thread-A did in or prior to a synchronized block is visible to Thread-B when it
   executes a synchronized block guarded by the same lock. That's why we synchronize getters and
   setters (not to ensure atomicity but visibility).

4. Use volatile variables only when they simplify implementing and verifying your synchronization
   policy; avoid using volatile variables when verifying correctness would require subtle reasoning
   about visibility.

5. see https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html