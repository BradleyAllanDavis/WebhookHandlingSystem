# Webhook Handling System

## Install and Run
1. Install [Nix package manager](https://determinate.systems/posts/determinate-nix-installer)
   - `curl --proto '=https' --tlsv1.2 -sSf -L https://install.determinate.systems/nix | sh -s -- install`
2. From project directory run `nix develop` to get a Nix dev shell
3. Run `mvn exec:java` to run application
4. Uninstall Nix `/nix/nix-installer uninstall`

## Future TODOs
- Add queue capacity
- Add thread pool
